package com.payments.coupon.domain.infrastructure;

import com.payments.coupon.domain.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    List<Optional<Coupon>> findByUserId(Long userId);

    void deleteById(Long couponId);
}
