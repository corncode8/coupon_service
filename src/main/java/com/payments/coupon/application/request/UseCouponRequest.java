package com.payments.coupon.application.request;

import com.payments.coupon.domain.entity.CouponType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UseCouponRequest {

    @NotNull
    private final long orderPrice;

    @NotNull
    private final CouponType couponType;
}
