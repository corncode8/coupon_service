package com.payments.domain.coupon.infrastructure;

import com.payments.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<User, Long> {
}
