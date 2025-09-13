package com.payments.coupon.application.request;

import com.payments.coupon.domain.entity.CouponType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class IssueCouponRequest {

    @NotNull
    private CouponType type;
}
