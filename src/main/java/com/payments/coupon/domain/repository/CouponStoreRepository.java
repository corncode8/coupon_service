package com.payments.coupon.domain.repository;

import com.payments.coupon.domain.entity.Coupon;

import java.util.List;


public interface CouponStoreRepository {
    Coupon save(Coupon coupon);

    void use(Long coupon);

    List<Coupon> findUserId(Long userId);
}
