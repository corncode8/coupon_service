package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.repository.CouponStoreRepository;
import com.payments.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.payments.support.response.BaseResponseStatus.*;

@Repository
@RequiredArgsConstructor
public class CouponCoreStoreRepository implements CouponStoreRepository {
    private final CouponJpaRepository repository;

    public Coupon save(Coupon coupon) {
        return repository.save(coupon);
    }

    public void use(Long userId) {
        repository.deleteById(userId);
    }

    public List<Coupon> findUserId(Long userId) {
        List<Coupon> coupons = repository.findByUserId(userId).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (coupons.isEmpty()) {
            throw new BaseException(NOT_FOUND_COUPON);
        }

        return coupons;
    }
}
