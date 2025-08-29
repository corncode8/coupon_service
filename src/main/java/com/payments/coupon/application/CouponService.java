package com.payments.coupon.application;

import com.payments.coupon.application.request.UseCouponRequest;
import com.payments.coupon.application.response.IssueCouponResponse;
import com.payments.coupon.application.response.UseCouponResponse;
import com.payments.coupon.entity.Coupon;
import com.payments.coupon.entity.CouponType;
import com.payments.coupon.repository.CouponReaderRepository;
import com.payments.coupon.repository.CouponStoreRepository;
import com.payments.support.exception.BaseException;
import com.payments.user.application.UserService;
import com.payments.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.payments.support.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponReaderRepository readerRepository;
    private final CouponStoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    public IssueCouponResponse issueCoupon (Long userId, CouponType type) {
        // 쿠폰 중복 발급 체크
        if (!readerRepository.findByIdForUpdate(userId, type).isPresent()) {
            throw new BaseException(ALREADY_ISSUED_COUPON);
        }

        User user = userService.findById(userId);

        Coupon coupon = Coupon.builder()
                .user(user)
                .type(type)
                .build();

        storeRepository.save(coupon);
        return new IssueCouponResponse(coupon.getId(), coupon.getType());
    }

    @Transactional
    public UseCouponResponse useCoupon (Long userId, UseCouponRequest request) {
        // 쿠폰이 있는지 확인
        if (readerRepository.findByIdForUpdate(userId, request.getCouponType()).isPresent()) {
            // 쿠폰 사용


        }

        
    }

}
