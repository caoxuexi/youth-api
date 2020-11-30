package com.cao.youth.api.v1;

import com.cao.youth.bo.PageCounter;
import com.cao.youth.core.LocalUser;
import com.cao.youth.core.interceptors.ScopeLevel;
import com.cao.youth.dto.OrderDTO;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.logic.CouponChecker;
import com.cao.youth.logic.OrderChecker;
import com.cao.youth.model.Order;
import com.cao.youth.service.OrderService;
import com.cao.youth.util.CommonUtil;
import com.cao.youth.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author 曹学习
 * @description OrderController
 * @date 2020/8/31 12:03
 */
@RequestMapping("order")
@RestController
@Validated
public class OrderController {
    @Value("${youth.order.pay-time-limit}")
    private Long payTimeLimit;

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    @ScopeLevel()
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid= LocalUser.getUser().getId();
        OrderChecker orderChecker = this.orderService.isOk(uid,orderDTO);
        Long oid = orderService.placeOrder(uid, orderDTO, orderChecker);
        return new OrderIdVO(oid);
    }


    @ScopeLevel()
    @GetMapping("/status/unpaid")
    public PagingDozer getUnpaid(@RequestParam(defaultValue = "0")
                                             Integer start,
                                             @RequestParam(defaultValue = "10")
                                             Integer count){
        PageCounter page= CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage= this.orderService.getUnpaid(page.getPage(), page.getCount());
        PagingDozer pagingDozer=new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o)->((OrderSimplifyVO)o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }
    
    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name="id") Long oid){
        Optional<Order> orderOptional=this.orderService.getOrderDetail(oid);
        return orderOptional.map((o)-> new OrderPureVO(o,payTimeLimit))
                .orElseThrow(()->{
                    throw new NotFoundException(50009);
                });
    }

    @ScopeLevel()
    @GetMapping("/by/status/{status}")
    public PagingDozer getByStatus(@PathVariable int status,
                                 @RequestParam(defaultValue = "0")
                                         Integer start,
                                 @RequestParam(defaultValue = "10")
                                         Integer count){
        PageCounter page= CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage= this.orderService.getByStatus(status,page.getPage(), page.getCount());
        PagingDozer pagingDozer=new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        pagingDozer.getItems().forEach((o)->((OrderSimplifyVO)o).setPeriod(this.payTimeLimit));
        return pagingDozer;
    }
}
