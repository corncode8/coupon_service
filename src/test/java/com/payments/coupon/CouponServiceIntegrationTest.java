package com.payments.coupon;

import com.payments.coupon.repository.CouponReaderRepository;
import com.payments.coupon.repository.CouponStoreRepository;
import com.payments.user.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;



@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CouponServiceIntegrationTest {

    @Autowired
    private CouponReaderRepository readerRepository;

    @Autowired
    private CouponStoreRepository storeRepository;

    @Autowired
    private UserService userService;

//    @DisplayName("쿠폰 발급 동시성 테스트")
//    @Test
//    void issueTest() {
//        //given
//
//        //when
//
//        //then
//
//    }
//
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
//
//
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
