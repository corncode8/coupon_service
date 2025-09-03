package com.payments.support.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {

    /**
     * 200 : 요청 성공
     */
    SUCCESS(true,HttpStatus.OK.value(), "요청에 성공하였습니다."),

    NOT_FIND_USER(false, HttpStatus.NOT_FOUND.value(), "일치하는 유저가 없습니다."),

    DUPLICATED_COUPON(false, HttpStatus.NOT_FOUND.value(), "이미 발급된 쿠폰이 존재합니다."),
    INVAILED_COUPON(false, HttpStatus.NOT_FOUND.value(), "유효하지 않은 쿠폰입니다."),
    LOW_ORDER_PRICE(false, HttpStatus.NOT_FOUND.value(), "결제 금액이 쿠폰 최소 결제 금액보다 작습니다."),

    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    DATABASE_EMPTY(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "저장된 데이터가 없습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),

    SCHEDULER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "스케줄러 오류 발생"),
    WAIT_QUEUE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "대기열 오류 발생"),
    WAIT_QUEUE_EMPTY(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "대기열에 유저가 없습니다."),
    INTERCEPTOR_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "인터셉터 오류 발생"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
