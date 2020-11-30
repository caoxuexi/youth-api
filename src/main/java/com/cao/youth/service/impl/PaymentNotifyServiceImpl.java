package com.cao.youth.service.impl;

import com.cao.youth.core.enumeration.OrderStatus;
import com.cao.youth.exception.http.ServerErrorException;
import com.cao.youth.model.Order;
import com.cao.youth.repository.OrderRepository;
import com.cao.youth.service.PaymentNotifyService;
import com.cao.youth.service.WxPaymentService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 曹学习
 * @description PaymentNotifyServiceImpl
 * @date 2020/9/9 23:42
 */
@Service
public class PaymentNotifyServiceImpl implements PaymentNotifyService {
    @Autowired
    private WxPaymentService wxPaymentService;

    @Autowired
    private OrderRepository orderRepository;

    //没有微信支付时使用
    @Transactional
    @Override
    public void processPayNotifyNoWx(Long oid) {
        Optional<String> orderNo=orderRepository.findOrderNoById(oid);
        orderNo.ifPresentOrElse(this::deal, new Runnable() {
            @Override
            public void run() {
                throw new ServerErrorException(9999);
            }
        });
    }

    @Transactional
    @Override
    public void processPayNotify(String data) {
        Map<String, String> dataMap;
        try {
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        boolean valid;
        try {
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }
        String resultCode = dataMap.get("result_code");
        String returnCode = dataMap.get("return_code");
        String orderNo = dataMap.get("out_trade_no");

        if (!returnCode.equals("SUCCESS") || !resultCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (orderNo == null) {
            throw new ServerErrorException(9999);
        }
        this.deal(orderNo);
    }

    private void deal(String orderNo) {
        Optional<Order> orderOptional = this.orderRepository.findFirstByOrderNo(orderNo);
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));
        //修改状态 unpaid->paid
        //支付前预扣除库存
        int res=-1;
        //不仅是未支付要改支付，还有一种可能是用户在订单取消前1s，点进去支付(但是1s到了订单状态变取消的情况)
        if (order.getStatus().equals(OrderStatus.UNPAID.value())
                || order.getStatus().equals(OrderStatus.CANCELED.value())) {
             res= this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
        if(res!=1){
            throw new ServerErrorException(9999);
        }
    }
}
