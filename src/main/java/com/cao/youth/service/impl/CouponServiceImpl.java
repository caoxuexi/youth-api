
package com.cao.youth.service.impl;

import com.cao.youth.core.enumeration.CouponStatus;
import com.cao.youth.exception.http.NotFoundException;
import com.cao.youth.exception.http.ParameterException;
import com.cao.youth.model.Activity;
import com.cao.youth.model.Coupon;
import com.cao.youth.model.User;
import com.cao.youth.model.UserCoupon;
import com.cao.youth.repository.ActivityRepository;
import com.cao.youth.repository.CouponRepository;
import com.cao.youth.repository.UserCouponRepository;
import com.cao.youth.service.CouponService;
import com.cao.youth.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 曹学习
 * @description CouponServiceImpl
 * @date 2020/8/29 22:38
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    public List<Coupon> getByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid, now);
    }

    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true, now);
    }

    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyAvailable(uid, now);
    }

    public List<Coupon> getMyUsedCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyUsed(uid, now);
    }

    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return this.couponRepository.findMyExpired(uid, now);
    }

    public void collectOneCoupon(Long uid, Long couponId){
        //1.找到这个couponId是否存在，这里我们不需要接受Coupon对象
        this.couponRepository
                .findById(couponId)
                .orElseThrow(() -> new NotFoundException(40003));

        //2.查找Coupon的领取时间是否过期了(Coupon和Activity挂钩的)
        Activity activity = this.activityRepository
                .findByCouponListId(couponId) //建立在join的关系之上
                .orElseThrow(() -> new NotFoundException(40010));
        Date now = new Date();
        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
        if(!isIn){
            throw new ParameterException(40005);
        }

        //3.检查用户是否已经领取了该优惠券
        this.userCouponRepository
                .findFirstByUserIdAndCouponId(uid, couponId)
                .ifPresent((uc)-> {throw new ParameterException(40006);});

        //4.新增用户和优惠券的关系
        UserCoupon userCouponNew = UserCoupon.builder()
                .userId(uid)
                .couponId(couponId)
                .status(CouponStatus.AVAILABLE.getValue())
                .createTime(now)
                .build();
        userCouponRepository.save(userCouponNew);
    }


//    public List<Coupon> getMyAvailableCoupons(Long uid) {
//        Date now = new Date();
//        return couponRepository.findMyAvailable(uid, now);
//    }
//
//    public List<Coupon> getMyUsedCoupons(Long uid) {
//        Date now = new Date();
//        return couponRepository.findMyUsed(uid, now);
//    }
//
//    public List<Coupon> getMyExpiredCoupons(Long uid) {
//        Date now = new Date();
//        return couponRepository.findUserExpired(uid, now);
//    }
//

//    public void writeOff(Long couponId) {
//
//    }

//    @Transactional
//    public void collectOneCoupon(Long uid, Long couponId) {
//        Date now = new Date();
//        // 很好的Optional案例
//        this.couponRepository
//                .findById(couponId)
//                .orElseThrow(()->new NotFound(40003));
//        Optional<Activity> optionalActivity = this.activityRepository.findByCouponListId(couponId);
//        Activity activity = optionalActivity
//                .orElseThrow(()->new NotFound(40010));
//
//        // TODO：有问题，应该判断活动是否过期
//        Boolean isIn = CommonUtil.isInTimeLine(now, activity.getStartTime(), activity.getEndTime());
//        if (!isIn) {
//            throw new ParameterException(40005);
//        }
//        UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId);
//        if (userCoupon != null) {
//            throw new ParameterException(40006);
//        }
//        UserCoupon userCouponNew = UserCoupon.builder()
//                .couponId(couponId)
//                .userId(uid)
//                .status(CouponStatus.AVAILABLE.value())
//                .createTime(now)
//                .build();
//        userCouponRepository.save(userCouponNew);
//    }

//    public Coupon getCoupon(Long id) {
//        return couponRepository.getOne(id);
//    }


//    public List<Coupon> getListByActivityId(Long aid) {
//        return null;
////        return couponRepository.findAllByActivityListId(aid);
//    }

}
