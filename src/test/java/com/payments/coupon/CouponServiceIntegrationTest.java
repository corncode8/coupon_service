package com.payments.coupon;

import com.payments.coupon.application.CouponService;
import com.payments.coupon.application.request.UseCouponRequest;
import com.payments.support.exception.BaseException;
import com.payments.support.response.BaseResponseStatus;
import com.payments.user.application.UserService;
import com.payments.user.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.payments.coupon.domain.entity.CouponType.*;
import static com.payments.support.response.BaseResponseStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class CouponServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CouponService couponService;

    @Container
    private static GenericContainer mySqlContainer = new MySQLContainer("mysql:8.0")
            .withReuse(true);

    /*
    * 같은 쿠폰을 여러 번 발급할 경우, 하나의 요청만 성공하고 나머지는 실패 처리
    * 한 명의 사용자에 대해 동시에 같은 타입 쿠폰 발급을 시도
    */
    @DisplayName("쿠폰 발급 동시성 테스트")
    @Test
    void issueTest() throws InterruptedException{
        //given
        User user = saveTestUser();

        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        int threadCnt = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);
        CountDownLatch latch = new CountDownLatch(threadCnt);
        List<BaseResponseStatus> exceptionList = new ArrayList<>();

        //when
        for (int i = 0 ; i < threadCnt; i++) {
            executorService.execute(() -> {
                try {
                    couponService.issueCoupon(user.getId(), DISCOUNT_10_PERCENT);
                    successCnt.incrementAndGet();
                } catch (BaseException e) {
                    exceptionList.add(e.getStatus());
                    failCnt.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //then
        assertEquals(1, successCnt.get(), "성공 1건");
        assertEquals(threadCnt - 1, failCnt.get(), "1건을 제외한 모든 스레드 실패");

        // 에러코드 일치 확인
        assertTrue(exceptionList.stream().allMatch(status -> status ==  DUPLICATED_COUPON));
    }

    private User saveTestUser() {
        User user = User.builder()
                .point(10000L)
                .build();
        userService.save(user);
        return user;
    }

    /*
    * 같은 쿠폰을 동시에 여러번 사용할 경우, 하나의 요청만 성공하고 나머지는 에러 처리
    * */
    @DisplayName("쿠폰 사용 동시성 테스트")
    @Test
    void useTest() throws Exception{
        //given
        User user = saveTestUser();
        couponService.issueCoupon(user.getId(), DISCOUNT_10_PERCENT);
        UseCouponRequest request = new UseCouponRequest(10000L, DISCOUNT_10_PERCENT);

        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);
        int threadCnt = 5;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);
        CountDownLatch latch = new CountDownLatch(threadCnt);

        //when
        for (int i = 0 ; i < threadCnt; i++) {
            executorService.execute(() -> {
                try {
                    couponService.useCoupon(user.getId(),request);
                    successCnt.incrementAndGet();
                } catch (ObjectOptimisticLockingFailureException e) {
                      failCnt.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();


        //then
        assertEquals(1, successCnt.get(), "성공 1건");
        assertEquals(threadCnt - 1, failCnt.get(), "1건을 제외한 모든 스레드 실패");

    }

}
