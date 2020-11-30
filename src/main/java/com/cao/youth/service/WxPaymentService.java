package com.cao.youth.service;

import com.github.wxpay.sdk.WXPay;

import java.util.Map;

/**
 * @author 曹学习
 * @description WxPaymentService
 * @date 2020/9/6 23:11
 */
public interface WxPaymentService {
    public Map<String,String> preOrder(Long oid);
    WXPay assembleWxPayConfig();
}
