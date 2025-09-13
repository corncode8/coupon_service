package com.payments.coupon.controller;

import com.payments.coupon.application.CouponService;
import com.payments.coupon.application.request.IssueCouponRequest;
import com.payments.coupon.application.request.UseCouponRequest;
import com.payments.coupon.application.response.IssueCouponResponse;
import com.payments.coupon.application.response.UseCouponResponse;
import com.payments.support.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/{userId}/coupons/issue")
    public BaseResponse<IssueCouponResponse> issueCoupon(
            @PathVariable final long userId,
            @RequestBody @Valid final IssueCouponRequest request
    ) {
        IssueCouponResponse execute = couponService.issueCoupon(userId, request.getType());

        return new BaseResponse<>(execute);
    }

    @PostMapping("/{userId}/coupons/use")
    public BaseResponse<UseCouponResponse> useCoupon(
            @PathVariable final long userId,
            @RequestBody @Valid final UseCouponRequest request
    ) {
        UseCouponResponse execute = couponService.useCoupon(userId, request);

        return new BaseResponse<>(execute);
    }
}
