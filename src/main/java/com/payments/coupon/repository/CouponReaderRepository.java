package com.payments.coupon.repository;


import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;

import java.util.Optional;

public interface CouponReaderRepository {

    Optional<Coupon> findByIdForUpdate(Long userId, CouponType type);
}
