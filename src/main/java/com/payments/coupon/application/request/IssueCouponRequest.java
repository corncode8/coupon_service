package com.payments.coupon.application.request;

import com.payments.coupon.entity.CouponType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueCouponRequest {

    @NotBlank
    private final CouponType type;
}
