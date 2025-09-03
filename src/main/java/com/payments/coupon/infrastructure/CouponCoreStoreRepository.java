package com.payments.coupon.infrastructure;

import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;
import com.payments.coupon.repository.CouponStoreRepository;
import com.payments.support.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    public Coupon findUserIdWithType(Long userId, CouponType type) {
        return repository.findByIdAndType(userId, type)
                .orElseThrow(() -> new BaseException(INVAILED_COUPON));
    }
}
