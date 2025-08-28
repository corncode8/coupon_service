package com.payments.domain.coupon.infrastructure;

import com.payments.domain.coupon.repository.CouponStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCoreStoreRepository implements CouponStoreRepository {
    private final CouponJpaRepository repository;
}
