package com.cao.youth.vo;

import com.cao.youth.model.Activity;
import com.cao.youth.model.Coupon;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ActivityCouponVO extends ActivityPureVO {
    private List<CouponPureVO> coupons;

    public ActivityCouponVO(Activity activity) {
        super(activity);
        coupons = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .collect(Collectors.toList());
    }
}
