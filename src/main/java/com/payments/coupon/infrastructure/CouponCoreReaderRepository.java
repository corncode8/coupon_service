package com.payments.coupon.infrastructure;

import com.payments.coupon.repository.CouponReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CouponCoreReaderRepository implements CouponReaderRepository {
    private final CouponJpaRepository repository;

}
