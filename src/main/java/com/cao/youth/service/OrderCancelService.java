package com.cao.youth.service;

import com.cao.youth.bo.OrderMessageBO;

/**
 * @author 曹学习
 * @description OrderCancelService
 * @date 2020/9/16 11:02
 */
public interface OrderCancelService {
    void cancel(OrderMessageBO messageBO);
}
