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
import org.springframework.dao.DataIntegrityViolationException;
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

    @Transactional(readOnly = true)
    public IssueCouponResponse issueCoupon (Long userId, CouponType type) {
        User user = userService.findById(userId);

        Coupon coupon = Coupon.builder()
                .user(user)
                .type(type)
                .build();

        // 쿠폰 중복 발급 체크
        try {
            storeRepository.save(coupon);
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(DUPLICATED_COUPON);
        }

        return new IssueCouponResponse(user.getId(), coupon.getType());
    }

    @Transactional
    public UseCouponResponse useCoupon(Long userId, UseCouponRequest request) {
        // Coupon Validation
        isValidCoupon(userId, request);

        // 쿠폰 이력 테이블에 Insert

        // 쿠폰 사용
        UseCouponResponse response = useCouponToResponse(request);
        storeRepository.use(userId);

        return response;
    }

    private void isValidCoupon(Long userId, UseCouponRequest request) {
        // 쿠폰이 있는지 확인
        Coupon coupon = storeRepository.findUserIdWithType(userId, request.getCouponType());

        // 쿠폰 사용 가격인지 확인
        switch (coupon.getType()) {
            case DISCOUNT_5000_WON:
                if (request.getOrderPrice() <= 20000) {
                    throw new BaseException(LOW_ORDER_PRICE);
                }
                break;
            case DISCOUNT_10000_WON:
                if (request.getOrderPrice() <= 50000) {
                    throw new BaseException(LOW_ORDER_PRICE);
                }
                break;
            case DISCOUNT_20_PERCENT:
                if (request.getOrderPrice() <= 30000) {
                    throw new BaseException(LOW_ORDER_PRICE);
                }
                break;
        }
    }

    private UseCouponResponse useCouponToResponse(UseCouponRequest request) {
        CouponType couponType = request.getCouponType();
        long discountPrice = 0;
        long finalPrice = request.getOrderPrice();

        switch (couponType) {
            case DISCOUNT_10_PERCENT:
                discountPrice = request.getOrderPrice() / 10 > 20000 ? 20000 : request.getOrderPrice() / 10;
                finalPrice -= discountPrice;
               return new UseCouponResponse(request.getOrderPrice(), discountPrice, finalPrice, request.getCouponType());
            case DISCOUNT_20_PERCENT:
                discountPrice = request.getOrderPrice() / 20 > 10000 ? 10000 : request.getOrderPrice() / 20;
                finalPrice -= discountPrice;
                return new UseCouponResponse(request.getOrderPrice(), discountPrice, finalPrice, request.getCouponType());
            case DISCOUNT_5000_WON:
                discountPrice = 5000;
                finalPrice -= discountPrice;
                return new UseCouponResponse(request.getOrderPrice(), discountPrice, finalPrice, request.getCouponType());
            case DISCOUNT_10000_WON:
                discountPrice = 10000;
                finalPrice -= discountPrice;
                return new UseCouponResponse(request.getOrderPrice(), discountPrice, finalPrice, request.getCouponType());
            default:
                throw new BaseException(UNEXPECTED_ERROR);
        }
    }

}
