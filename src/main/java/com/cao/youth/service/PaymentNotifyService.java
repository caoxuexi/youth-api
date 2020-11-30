package com.cao.youth.service;


/**
 * @author 曹学习
 * @description PaymentNotifyService
 * @date 2020/9/9 23:42
 */
public interface PaymentNotifyService {
    void processPayNotify(String data);
    void processPayNotifyNoWx(Long oid);
}
