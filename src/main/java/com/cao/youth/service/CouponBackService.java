package com.cao.youth.service;

import com.cao.youth.bo.OrderMessageBO;

/**
 * @author 曹学习
 * @description CouponBackService
 * @date 2020/9/16 11:03
 */
public interface CouponBackService {
    void returnBack(OrderMessageBO bo);
}