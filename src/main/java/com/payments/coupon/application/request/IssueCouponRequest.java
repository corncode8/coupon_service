package com.payments.coupon.application.request;

import com.payments.coupon.entity.CouponType;
import lombok.Data;

@Data
public class IssueCouponRequest {
    private final CouponType type;
}
