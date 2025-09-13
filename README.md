## 쿠폰 서비스

- 쿠폰 발급 API
  - 한 사람당 쿠폰은 종류별로 한 개씩만 발급 가능
  - 같은 쿠폰을 여러번 발급할 경우, 하나의 요천만 성공하고 나머지 요청은 에러처리
- 쿠폰 사용 API
  - 쿠폰은 한 번만 사용되어야 한다.
  - 같은 쿠폰을 동시에 여러번 사용할 경우, 하나의 요청만 성공하고 나머지는 에러처리
  - 결제 금액은 10원단위로 결제 금액에 1원단위가 있을 경우 에러처리
  - 쿠폰 자동선택
      - 보유한 쿠폰 중 가장 할인 금액이 큰 쿠폰이 자동으로 사용되어야 한다.
      - 할인 금액이 같다면 먼저 발급된 쿠폰이 사용되어야 한다.

### Description

- 쿠폰 발급
    - 쿠폰 발급시 동시성을 제어하기 위해 MySQL의 유니크 인덱스를 활용
- 쿠폰 사용
  - 낙관적 락을 이용하여 쿠폰 동시 사용 제어
<br/>

### API 명세
#### 1. 쿠폰 발급
| 메서드 | 요청 URL | 기능 |
|--------|-----------------| -----------|
| POST    |/api/{userId}/coupons/issue     | 쿠폰 발급   |

<br/>

Request 
| 필드 | 타입 | 필수여부 | 설명 |
|--------|----------| ----------|----------|
| type  |String    |필수        |발급할 쿠폰 타입|
- 허용값: DISCOUNT_10_PERCENT, DISCOUNT_20_PERCENT, DISCOUNT_5000_WON, DISCOUNT_10000_WON
  
``` C
{
  "type": "DISCOUNT_10_PERCENT"
}
```

<br/>

Response
``` C
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "result": {
    "userId": 1,
    "type": "DISCOUNT_10_PERCENT"
  }
}
```
<br/>

#### 2. 쿠폰 사용
| 메서드 | 요청 URL | 기능 |
|--------|-----------------| -----------|
| POST    |/api/{userId}/coupons/use     | 쿠폰 사용   |

<br/>

Request 
| 필드 | 타입 | 필수여부 | 설명 |
|--------|----------| ----------|----------|
| orderPrice  |String    |필수        |주문 금액|
| couponType  |String    |필수        |사용할 쿠폰 타입|


``` C
{
  "orderPrice": 10000,
  "couponType": "DISCOUNT_10_PERCENT"
}
```

<br/>

Response
``` C
{
  "isSuccess": true,
  "code": 200,
  "message": "요청에 성공하였습니다.",
  "result": {
    "orderPrice": 10000,
    "discountPrice": 1000,
    "finalPrice": 9000,
    "couponType": "DISCOUNT_10_PERCENT"
  }
}
```


