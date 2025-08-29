package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;
import com.payments.coupon.repository.CouponReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponCoreReaderRepository implements CouponReaderRepository {
    private final CouponJpaRepository repository;

    public Optional<Coupon> findByIdForUpdate(Long userId, CouponType type) {
        return repository.findByIdForUpdate(userId, type);
    }
}
