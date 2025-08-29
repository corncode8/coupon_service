package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;
import com.payments.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT * FROM Coupon c WHERE c.user_id = :userId " +
            "                        AND type = :type  FOR UPDATE")
    Optional<Coupon> findByIdForUpdate(Long userId, CouponType type);

}
