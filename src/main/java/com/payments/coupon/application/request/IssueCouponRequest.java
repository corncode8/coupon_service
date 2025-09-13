package com.payments.coupon.application.request;

import com.payments.coupon.domain.entity.CouponType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueCouponRequest {

    @NotBlank
    private final CouponType type;
}
