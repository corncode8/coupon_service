package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.repository.CouponStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCoreStoreRepository implements CouponStoreRepository {
    private final CouponJpaRepository repository;

    public Coupon save(Coupon coupon) {
        return repository.save(coupon);
    }
}
