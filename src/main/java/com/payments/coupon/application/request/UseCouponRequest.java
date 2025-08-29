package com.payments.coupon.application.request;

import com.payments.coupon.entity.CouponType;
import lombok.Data;

@Data
public class UseCouponRequest {
    private final long orderPrice;
    private final CouponType couponType;
}
