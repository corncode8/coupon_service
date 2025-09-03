package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByIdAndType(Long userId, CouponType type);

    void deleteById(Long couponId);
}
