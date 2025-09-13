package com.payments.coupon;

import com.payments.coupon.application.CouponService;
import com.payments.coupon.application.request.UseCouponRequest;
import com.payments.coupon.application.response.UseCouponResponse;
import com.payments.coupon.domain.entity.Coupon;
import com.payments.coupon.domain.entity.CouponType;
import com.payments.coupon.domain.repository.CouponReaderRepository;
import com.payments.coupon.domain.repository.CouponStoreRepository;
import com.payments.support.common.BaseEntity;
import com.payments.support.exception.BaseException;
import com.payments.user.application.UserService;
import com.payments.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static com.payments.support.response.BaseResponseStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponReaderRepository readerRepository;

    @Mock
    private CouponStoreRepository storeRepository;

    @Mock
    private UserService userService;

    private CouponService couponService;

    @BeforeEach
    void setUp() {
        couponService = new CouponService(readerRepository, storeRepository, userService);
    }

    @DisplayName("isValidCoupon 성공 테스트 - 10% 쿠폰 보유")
    @Test
    void isValidCouponTest() {
        //given
        Long userId = Mockito.anyLong();
        UseCouponRequest request = new UseCouponRequest(30000L, CouponType.DISCOUNT_10_PERCENT);

        List<Coupon> coupons = List.of(
                Coupon.builder().type(CouponType.DISCOUNT_10_PERCENT).build(),
                Coupon.builder().type(CouponType.DISCOUNT_5000_WON).build()
        );
        Mockito.when(storeRepository.findUserId(userId)).thenReturn(coupons);

        //when
        List<Coupon> result = couponService.isValidCoupon(userId, request);

        //then
        assertEquals(coupons, result);

    }

    @DisplayName("isValidCoupon 예외 테스트 - 결제 금액 오류")
    @Test
    void isValidCouponOrderPriceErrorTest() {
        //given
        UseCouponRequest request = new UseCouponRequest(4001L, CouponType.DISCOUNT_10_PERCENT);

        User user = User.builder().point(50000L).build();

        List<Coupon> coupons = List.of(
                Coupon.builder().user(user).type(CouponType.DISCOUNT_10_PERCENT).build()
        );

        Mockito.when(storeRepository.findUserId(user.getId())).thenReturn(coupons);

        //when
        BaseException exception = assertThrows(BaseException.class, () -> {
            couponService.isValidCoupon(user.getId(), request);
        });

        //then
        assertEquals(ORDER_PRICE_ERROR, exception.getStatus());
    }

    @DisplayName("isValidCoupon 예외 테스트 - 쿠폰 최소 금액 오류")
    @Test
    void isValidCouponCouponErrorTest() {
        //given
        UseCouponRequest request = new UseCouponRequest(4000L, CouponType.DISCOUNT_5000_WON);

        User user = User.builder().point(50000L).build();

        List<Coupon> coupons = List.of(
                Coupon.builder().user(user).type(CouponType.DISCOUNT_5000_WON).build()
        );

        Mockito.when(storeRepository.findUserId(user.getId())).thenReturn(coupons);

        //when
        BaseException exception = assertThrows(BaseException.class, () -> {
            couponService.isValidCoupon(user.getId(), request);
        });

        //then
        assertEquals(LOW_ORDER_PRICE, exception.getStatus());

    }

    @DisplayName("matchBestCoupon 테스트")
    @Test
    void matchBestCouponTest() throws NoSuchFieldException, IllegalAccessException{
        //given
        UseCouponRequest request = new UseCouponRequest(50000L, CouponType.DISCOUNT_10_PERCENT);

        User user = User.builder().point(100000L).build();

        List<Coupon> coupons = List.of(
                Coupon.builder().user(user).type(CouponType.DISCOUNT_10_PERCENT).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_20_PERCENT).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_5000_WON).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_10000_WON).build()
        );

        Field createdAt = BaseEntity.class.getDeclaredField("createdAt");
        createdAt.setAccessible(true);

        for (Coupon coupon : coupons) {
           createdAt.set(coupon, LocalDateTime.now().minusDays(2));
        }

        //when
        Coupon coupon = couponService.matchBestCoupon(coupons, request.getOrderPrice());

        //then
        assertEquals(CouponType.DISCOUNT_20_PERCENT, coupon.getType());

    }

    @ParameterizedTest
    @DisplayName("calculateDiscount 테스트")
    @MethodSource("provideMatchBestCouponCases")
    void matchBestCouponParameterizedTest(long orderPrice, CouponType expectedType) throws Exception {
        //given
        User user = User.builder().point(100000L).build();

        List<Coupon> coupons = List.of(
                Coupon.builder().user(user).type(CouponType.DISCOUNT_10_PERCENT).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_20_PERCENT).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_5000_WON).build(),
                Coupon.builder().user(user).type(CouponType.DISCOUNT_10000_WON).build()
        );

        Field createdAt = BaseEntity.class.getDeclaredField("createdAt");
        createdAt.setAccessible(true);
        for (Coupon coupon : coupons) {
            createdAt.set(coupon, LocalDateTime.now().minusDays(2));
        }

        //when
        Coupon coupon = couponService.matchBestCoupon(coupons, orderPrice);

        //then
        assertEquals(expectedType, coupon.getType());
    }

    static List<Arguments> provideMatchBestCouponCases() {
        return List.of(
                Arguments.of(50000L, CouponType.DISCOUNT_20_PERCENT),
                Arguments.of(100000L, CouponType.DISCOUNT_10_PERCENT),
                Arguments.of(25000L, CouponType.DISCOUNT_10000_WON)
        );
    }



    @DisplayName("useCouponToResponse 테스트")
    @Test
    void useCouponToResponseTest() {
        //given
        Long orderPrice = 100000L;
        CouponType[] types = {
                CouponType.DISCOUNT_10_PERCENT,
                CouponType.DISCOUNT_20_PERCENT,
                CouponType.DISCOUNT_5000_WON,
                CouponType.DISCOUNT_10000_WON
        };

        //when
        for (CouponType type : types) {
            long expectiedDiscount = 0;

            switch (type) {
                case DISCOUNT_10_PERCENT:
                    expectiedDiscount = Math.min(orderPrice / 10, 20000);
                    break;
                case DISCOUNT_20_PERCENT:
                    expectiedDiscount = Math.min(orderPrice / 5, 10000);
                    break;
                case DISCOUNT_5000_WON:
                    expectiedDiscount = 5000;
                    break;
                case DISCOUNT_10000_WON:
                    expectiedDiscount = 10000;
                    break;
            }

            UseCouponResponse actual = couponService.useCouponToResponse(orderPrice, type);
            UseCouponResponse expected = new UseCouponResponse(orderPrice, expectiedDiscount, orderPrice - expectiedDiscount, type);

            //then
            assertEquals(expectiedDiscount, actual.getDiscountPrice());
            assertEquals(expected, actual);
        }

    }
}


