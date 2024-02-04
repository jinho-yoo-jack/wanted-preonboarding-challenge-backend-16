## 🧑🏻‍💻 구현한 기능
- 공연 예약
- 공연 정보 조회
- 공연 취소 (알림 전송)
- 예약 정보 조회

## 🧩 Table
<img width="1100" alt="image" src="https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/99247279/40660099-28c9-4b8d-bca9-090e9da9f2db">

- 2개의 테이블을 추가하였습니다.
- ***DISCOUNT***
  - 공연에 대한 할인 정보를 담는 테이블입니다. 
  - 하나의 공연에 여러가지 할인이 적용될 수 있다고 생각하여 일대다 관계를 설정하였습니다.
- ***NOTIFICATION***
  - 공연에 대한 알림 정보를 담는 테이블입니다. 
  - 알림은 공연말고도 다양한 도메인에서 사용될 수 있을것이라 생각하여 targetId로 설정한 도메인에 이벤트가 발생한다면 알림을 전송할 수 있도록 하였습니다.

&nbsp;

## 💰 할인
```java
public interface DiscountManager {
    double applyDiscount(PaymentInfo paymentInfo);
}
```

```java
@RequiredArgsConstructor
@Service
public class DefaultDiscountManager implements DiscountManager {

    private final List<DiscountPolicy> discountPolicies;

    @Override
    public double applyDiscount(final PaymentInfo paymentInfo) {
        int originPrice = paymentInfo.price();

        for (DiscountPolicy policy : discountPolicies) {
            // 할인 로직 적용
        }
        return originPrice;
    }

}
```

- 할인 로직을 구현하기 위해 다음의 인터페이스를 선언하였습니다.
  - **DiscountManager**: 초기 금액에서 할인이 적용된 금액을 반환받음
  - **DiscountPolicy**: 각 할인 정책에 따라 할인 금액을 반환
- 다양한 할인 정책이 적용될 수 있기때문에 DiscountManager는 주입받은 여러 DiscountPolicy 구현체의 할인 로직을 적용시켜 최종적으로 할인이 적용된 금액을 반환합니다.

&nbsp;

## 🔔 알림
```java
/*
* getMessage() -> 알림 메시지
* getTargetId() -> 알림 targetId (ex - performanceId)
* */
public interface NotificationHolder {
    String getMessage();
    String getTargetId();
}
```

```java
/*
* 예약 취소 이벤트 (알림 이벤트)
*/
public record ReservationCancelEvent(CancelReservationInfo reservationInfo) implements NotificationHolder {

    @Override
    public String getMessage() {
        return NotificationMessageFormat.getEmptyPerformanceMessage(reservationInfo);
    }

    @Override
    public String getTargetId() {
        return reservationInfo.performanceId().toString();
    }
}
```

- 알림 메시지를 생성하는데 필요한 데이터는 다양한 형태가 될 수 있을 것이라 판단하여 메시지를 가져오는 부분을 NotificationHolder 인터페이스로 추상화하였습니다.

&nbsp;

```java
// 이벤트 발행 (알림 전송)
eventPublisher.publishEvent(new ReservationCancelEvent(cancelInfo));
```

- 서비스 로직의 의존성을 분리하기위해 Spring Event를 사용하여 알림을 전송하도록 하였습니다.
- @TransactionalEventListener 를 사용하여 예약 취소가 성공적으로 반영된 후 알림을 전송하도록 하였습니다.
  - @TransactionalEventListener의 기본 phase 값은 AFTER_COMMIT 입니다

&nbsp;

```java
// COMMIT 후 알림 전송 (비동기)
@Retry
@Async
@TransactionalEventListener
public void sendNotification(final NotificationHolder holder) {
    // 알림 메시지 생성
    String message = holder.getMessage();

    // 알림 대상 조회
    String targetId = holder.getTargetId();
    // 대상(target) 조회 로직

    // 알림 전송
    notificationSender.send(message, target);
}
```

- 특정 데이터 타입이 아닌 **NotificationHolder**에 의존하고있기 때문에 내부적으로 어떤 데이터를 가지고있는지 전혀 신경쓰지않고 알림 메시지를 반환받을 수 있습니다.

&nbsp;

```java
@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object retry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxRetry = retry.time();
        Exception exceptionHolder = null;

        for (int count = 1; count < maxRetry; count++) {
            try {
                return joinPoint.proceed();
            } catch (Exception exception) {
                log.warn("[retry] Exception has occurred (count: {})", count);
                exceptionHolder = exception;
            }
        }
        throw Objects.requireNonNull(exceptionHolder);
    }
}
```

- AOP를 통해 알림 전송 도중 문제가 발생했다면 지정된 횟수만큼 다시 시도하도록 구현하였습니다.

&nbsp;
<img width="500" alt="image" src="https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/99247279/f7a951dc-7757-4932-bd27-8fffee4c968f">


## 테스트코드
<img width="1451" alt="image" src="https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/99247279/0689e198-4f1a-469c-b274-2005753ca816">

- Service Layer Test는 @SpringBootTest, Repository Layer Test는 @DataJpaTest를 사용하여 테스트하였습니다.
- QueryDSL 관련 설정 정보를 Import하는 @Repository 커스텀 어노테이션을 생성하여 사용하였습니다.
- 47개의 테스트를 작성하였습니다.

## 아쉬웠던점
- Naming convention을 제대로 지키지 못한 것이 아쉽습니다. 그때그때 필요한 클래스를 생성하다보니 반환되는 Dto postfix 가 Info인 것도 있고 Model인 것도 있네요. 명확한 기준을 통한 네이밍 컨벤션이 필요하다고 느꼈습니다.
