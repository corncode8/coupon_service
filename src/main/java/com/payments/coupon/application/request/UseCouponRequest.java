package com.payments.coupon.application.request;

import com.payments.coupon.entity.CouponType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UseCouponRequest {

    @NotNull
    private final long orderPrice;

    @NotBlank
    private final CouponType couponType;
}
