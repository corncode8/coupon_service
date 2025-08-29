package com.payments.coupon.application.response;

import com.payments.coupon.entity.CouponType;
import lombok.Data;

@Data
public class UseCouponResponse {

    // 정상가
    private final long originalPrice;

    // 할인 금액
    private final long discountPrice;

    // 최종 결제 금액
    private final long finalPrice;

    // 적용된 쿠폰 타입
    private final CouponType couponType;

}
