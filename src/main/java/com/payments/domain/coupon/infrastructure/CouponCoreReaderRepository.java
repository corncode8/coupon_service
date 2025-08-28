package com.payments.domain.coupon.infrastructure;

import com.payments.domain.coupon.repository.CouponReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCoreReaderRepository implements CouponReaderRepository {
    private final CouponJpaRepository repository;
}
