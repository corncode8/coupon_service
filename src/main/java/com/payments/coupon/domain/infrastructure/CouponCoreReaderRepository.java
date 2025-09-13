package com.payments.coupon.domain.infrastructure;

import com.payments.coupon.domain.repository.CouponReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CouponCoreReaderRepository implements CouponReaderRepository {
    private final CouponJpaRepository repository;

}
