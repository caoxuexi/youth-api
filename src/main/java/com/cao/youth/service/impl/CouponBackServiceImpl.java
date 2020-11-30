package com.cao.youth.service.impl;

import com.cao.youth.bo.OrderMessageBO;
import com.cao.youth.core.enumeration.OrderStatus;
import com.cao.youth.exception.http.ServerErrorException;
import com.cao.youth.model.Order;
import com.cao.youth.repository.OrderRepository;
import com.cao.youth.repository.UserCouponRepository;
import com.cao.youth.service.CouponBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 曹学习
 * @description CouponBackServiceImpl
 * @date 2020/9/16 11:03
 */
@Service
public class CouponBackServiceImpl implements CouponBackService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    @Transactional
    public void returnBack(OrderMessageBO bo) {
        Long couponId=bo.getCouponId();
        Long uid=bo.getUserId();
        Long orderId=bo.getOrderId();
        if(couponId==-1){
            return;
        }
        Optional<Order> optional=orderRepository.findFirstByUserIdAndId(uid,orderId);
        Order order=optional.orElseThrow(()->{
            throw new ServerErrorException(9999);
        });

        if(order.getStatusEnum().equals(OrderStatus.UNPAID)||
        order.getStatusEnum().equals(OrderStatus.CANCELED)){
            //未支付和取消的情况才回退优惠券
            this.userCouponRepository.returnBack(couponId,uid);
        }
    }
}
