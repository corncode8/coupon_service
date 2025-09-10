package com.payments.coupon;

import com.payments.coupon.application.CouponService;
import com.payments.coupon.application.response.IssueCouponResponse;
import com.payments.coupon.entity.CouponType;
import com.payments.coupon.repository.CouponReaderRepository;
import com.payments.coupon.repository.CouponStoreRepository;
import com.payments.user.application.UserService;
import com.payments.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CouponServiceIntegrationTest {

    @Autowired
    private CouponReaderRepository readerRepository;

    @Autowired
    private CouponStoreRepository storeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Container
    private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
            .withReuse(true);

    /*
    * 한 사람당 쿠폰은 종류별로 한 개만 발급받을 수 있다.
    * 같은 쿠폰을 여러 번 발급할 경우, 하나의 요청만 성공하고 나머지는 실패 처리
    */
//    @DisplayName("쿠폰 발급 동시성 테스트")
//    @Test
//    void issueTest() {
//        //given
//        User user = User.builder().point(10000L).build();
//
//        //when
//        IssueCouponResponse issueCouponResponse = couponService.issueCoupon(user.getId(), CouponType.DISCOUNT_10_PERCENT);
//
//        //then
//        assertEquals(user.getId(), issueCouponResponse.getUserId());
//        assertEquals(CouponType.DISCOUNT_10_PERCENT, issueCouponResponse.getType());
//
//    }

//    @DisplayName("쿠폰 사용 동시성 테스트")
//    @Test
//    void useTest() {
//        //given
//
//        //when
//
//        //then
//
//    }


//    @DisplayName("쿠폰 발급 and 사용 테스트")
//    @Test
//    void test() {
//        //given
//
//        //when
//
//        //then
//
//    }

}
