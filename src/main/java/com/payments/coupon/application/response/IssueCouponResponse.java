package com.payments.coupon.application.response;

import com.payments.coupon.entity.CouponType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class IssueCouponResponse {

    public final Long userId;
    public final CouponType type;
}
