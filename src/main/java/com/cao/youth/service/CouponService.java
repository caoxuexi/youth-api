package com.cao.youth.service;

import com.cao.youth.model.Coupon;

import java.util.List;

/**
 * @author 曹学习
 * @description CouponService
 * @date 2020/8/29 12:42
 */
public interface CouponService {
    List<Coupon> getByCategory(Long cid);
    List<Coupon> getWholeStoreCoupons();
    List<Coupon> getMyAvailableCoupons(Long uid);
    List<Coupon> getMyUsedCoupons(Long uid);
    List<Coupon> getMyExpiredCoupons(Long uid);
    void collectOneCoupon(Long uid, Long couponId);

}
