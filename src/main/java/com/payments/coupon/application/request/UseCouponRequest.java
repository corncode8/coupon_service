package com.payments.coupon.application.request;

import com.payments.coupon.domain.entity.CouponType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UseCouponRequest {

    @NotNull
    private long orderPrice;

    @NotNull
    private CouponType couponType;
}
