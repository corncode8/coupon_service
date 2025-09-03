package com.payments.coupon.repository;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;

public interface CouponStoreRepository {
    Coupon save(Coupon coupon);

    void use(Long coupon);

    Coupon findUserIdWithType(Long userId, CouponType type);
}
