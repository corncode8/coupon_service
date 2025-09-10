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

import java.util.List;

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
        List<Coupon> validCoupon = isValidCoupon(userId, request);

        // 쿠폰 이력 테이블에 Insert

        // 쿠폰 사용
        Coupon coupon = matchBestCoupon(validCoupon, request.getOrderPrice());
        UseCouponResponse response = useCouponToResponse(request.getOrderPrice(), coupon.getType());

        try {
            storeRepository.use(coupon.getCouponId());
        } catch (Exception e) {
            throw new BaseException(USE_COUPON_ERROR);
        }


        return response;
    }

    public List<Coupon> isValidCoupon(Long userId, UseCouponRequest request) {
        // 원단위 결제금액 및 10원 미만 금액 확인
        if (request.getOrderPrice() % 10 != 0 || request.getOrderPrice() < 10) {
            throw new BaseException(ORDER_PRICE_ERROR);
        }

        // 쿠폰이 있는지 확인
        List<Coupon> couponList = storeRepository.findUserId(userId);

        boolean has10Percent = couponList.stream()
                .anyMatch(coupon -> coupon.getType().equals(CouponType.DISCOUNT_10_PERCENT));

        if (!has10Percent) {
            for (Coupon coupon : couponList) {
                // 쿠폰 사용 가격인지 확인
                switch (coupon.getType()) {
                    case DISCOUNT_5000_WON:
                        if (request.getOrderPrice() <= 20000) {
                            throw new BaseException(LOW_ORDER_PRICE);
                        }
                        return couponList;
                    case DISCOUNT_20_PERCENT:
                        if (request.getOrderPrice() <= 30000) {
                            throw new BaseException(LOW_ORDER_PRICE);
                        }
                        return couponList;
                    case DISCOUNT_10000_WON:
                        if (request.getOrderPrice() <= 50000) {
                            throw new BaseException(LOW_ORDER_PRICE);
                        }
                        break;
                }
            }
        }
        return couponList;
    }

    public Coupon matchBestCoupon(List<Coupon> coupons, long orderPrice) {
        Coupon bestCoupon = null;
        long maxDiscount = Long.MIN_VALUE;

        for (Coupon coupon : coupons) {
            long discount = calculateDiscount(coupon.getType(), orderPrice);
            if (bestCoupon == null
                    || discount > maxDiscount
                    || (discount == maxDiscount && coupon.getCreatedAt().isBefore(bestCoupon.getCreatedAt()))) {
                bestCoupon = coupon;
                maxDiscount = discount;
            }
        }

        if (bestCoupon == null) {
            throw new BaseException(NOT_FOUND_COUPON);
        }

        return bestCoupon;
    }

    public long calculateDiscount(CouponType type, long orderPrice) {
        switch (type) {
            case DISCOUNT_10_PERCENT:
                return Math.min(orderPrice / 10, 20000);
            case DISCOUNT_20_PERCENT:
                return Math.min(orderPrice / 5, 10000);
            case DISCOUNT_5000_WON:
                return 5000;
            case DISCOUNT_10000_WON:
                return 10000;
            default:
                return 0;
        }
    }

    public UseCouponResponse useCouponToResponse(Long orderPrice, CouponType couponType) {
        long discountPrice = 0;
        long finalPrice = orderPrice;

        switch (couponType) {
            case DISCOUNT_10_PERCENT:
                discountPrice = Math.min(orderPrice / 10, 20000);
                finalPrice -= discountPrice;
               return new UseCouponResponse(orderPrice, discountPrice, finalPrice, couponType);
            case DISCOUNT_20_PERCENT:
                discountPrice = Math.min(orderPrice / 5, 10000);
                finalPrice -= discountPrice;
                return new UseCouponResponse(orderPrice, discountPrice, finalPrice, couponType);
            case DISCOUNT_5000_WON:
                discountPrice = 5000;
                finalPrice -= discountPrice;
                return new UseCouponResponse(orderPrice, discountPrice, finalPrice, couponType);
            case DISCOUNT_10000_WON:
                discountPrice = 10000;
                finalPrice -= discountPrice;
                return new UseCouponResponse(orderPrice, discountPrice, finalPrice, couponType);
            default:
                throw new BaseException(UNEXPECTED_ERROR);
        }
    }

}
