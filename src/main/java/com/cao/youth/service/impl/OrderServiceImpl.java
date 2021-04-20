package com.cao.youth.service.impl;

import com.cao.youth.core.LocalUser;
import com.cao.youth.core.enumeration.OrderStatus;
import com.cao.youth.core.money.IMoneyDiscount;
import com.cao.youth.dto.OrderDTO;
import com.cao.youth.dto.SkuInfoDTO;
import com.cao.youth.exception.http.ForbiddenException;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.logic.CouponChecker;
import com.cao.youth.logic.OrderChecker;
import com.cao.youth.model.*;
import com.cao.youth.repository.CouponRepository;
import com.cao.youth.repository.OrderRepository;
import com.cao.youth.repository.SkuRepository;
import com.cao.youth.repository.UserCouponRepository;
import com.cao.youth.service.OrderService;
import com.cao.youth.service.SkuService;
import com.cao.youth.util.CommonUtil;
import com.cao.youth.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 曹学习
 * @description OrderServiceImpl
 * @date 2020/8/31 12:16
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private IMoneyDiscount iMoneyDiscount;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${youth.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${youth.order.pay-time-limit}")
    private Integer payTimeLimit;

    @Override
    public OrderChecker isOk(Long uid, OrderDTO orderDTO) {
        if(orderDTO.getFinalTotalPrice().compareTo(new BigDecimal("0"))<=0){
            //最终价格小于0
            throw new ParameterException(50011);
        }

        //将sku的id存入列表
        List<Long> skuIdList=orderDTO.getSkuInfoList()
                .stream()
                .map(SkuInfoDTO::getId)
                .collect(Collectors.toList());

        List<Sku> skuList= skuService.getSkuListByIds(skuIdList);

        //从order中获取使用的Coupon
        Long couponId=orderDTO.getCouponId();
        CouponChecker couponChecker=null;
        if(couponId!=null){
            Coupon coupon =this.couponRepository.findById(couponId)
                    .orElseThrow(()->new NotFoundException(40004));
            //查找用户是否有这个Coupon
            UserCoupon userCoupon=this.userCouponRepository.findFirstByUserIdAndCouponIdAndStatus(uid,couponId,1)
                    .orElseThrow(()->new NotFoundException(50006));
            couponChecker=new CouponChecker(coupon,iMoneyDiscount);
        }
        OrderChecker orderChecker=new OrderChecker(
                orderDTO,skuList,couponChecker,this.maxSkuLimit
        );
        orderChecker.isOK();
        return orderChecker;
    }

    private void reduceStock(OrderChecker orderChecker){
        List<OrderSku> orderSkuList=orderChecker.getOrderSkuList();
        for (OrderSku orderSku : orderSkuList) {
            int result = this.skuRepository.reduceStock(orderSku.getId(), orderSku.getCount().longValue());
            if(result!=1){
                throw new ParameterException(40003);
            }
        }
    }

    private void writeOffCoupon(Long couponId,Long oid,Long uid){
        int result=this.userCouponRepository.writeOff(couponId,oid,uid);
        if(result!=1){
            throw new ForbiddenException(40012);
        }
    }

    @Transactional//导入Spring包下的
    @Override
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo= OrderUtil.makeOrderNo();
        Calendar nowPre=Calendar.getInstance();
        Calendar now=(Calendar) nowPre.clone();
        Date expiredTime=CommonUtil.addSomeSeconds(nowPre,this.payTimeLimit).getTime();

        Order order= Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())//因为已经校验过了，所以直接从orderDTO中获取
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid)
                .totalCount(orderChecker.getTotalCount().longValue())
                .snapImg(orderChecker.getLeaderImg())
                .snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
                .expiredTime(expiredTime)
                .placedTime(now.getTime())
                .build();
        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        this.orderRepository.save(order);
        this.reduceStock(orderChecker);
        Long couponId=-1L;
        if(orderDTO.getCouponId()!=null){
            this.writeOffCoupon(orderDTO.getCouponId(),order.getId(),uid);
            couponId=orderDTO.getCouponId();
        }
        // 数据加入到延迟消息队列里
        this.sendToRedis(order.getId(),uid,couponId);
        return order.getId();//这里也可以用orderNo，但是它是字符串没有数字的查询速度快
    }

    @Override
    public Page<Order> getUnpaid(Integer page, Integer size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("createTime").descending());
        Long uid= LocalUser.getUser().getId();
        Date now=new Date();
        return orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(now,OrderStatus.UNPAID.value(),uid,pageable);
    }

    @Override
    public Page<Order> getByStatus(Integer status,Integer page,Integer size){
        Pageable pageable= PageRequest.of(page,size, Sort.by("createTime").descending());
        Long uid= LocalUser.getUser().getId();
        if(status ==OrderStatus.All.value()){
            return this.orderRepository.findByUserId(uid,pageable);
        }
        return this.orderRepository.findByUserIdAndStatus(uid,status,pageable);
    }

    @Override
    public Optional<Order> getOrderDetail(Long oid) {
        Long uid= LocalUser.getUser().getId();
        return this.orderRepository.findFirstByUserIdAndId(uid,oid);
    }

    private void sendToRedis(Long oid,Long uid,Long couponId){
        String key=oid.toString()+","+uid.toString()+","+couponId.toString();
        try {
            stringRedisTemplate.opsForValue().set(key,"1",this.payTimeLimit, TimeUnit.SECONDS);//value随便填
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderPrepayId(Long orderId, String prePayId) {
        Optional<Order> order=this.orderRepository.findById(orderId);
        order.ifPresentOrElse(o->{
            o.setPrepayId(prePayId);
            this.orderRepository.save(o);
        },()->{
            throw new ParameterException(10007);
        });
    }

    @Override
    public void confirmReceipt(Long oid) {
        Optional<Order> orderOptional = this.orderRepository.findById(oid);
        Order order = orderOptional.orElseThrow(() -> new NotFoundException(50009));
    }
}
