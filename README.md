# 도메인 다이어그램
![스크린샷 2024-01-31 오후 11 54 58](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/47974623/0450cff3-c27c-4b6c-91bf-2525cebec54e)


## 1. Perform 과 Perfomance
![스크린샷 2024-01-31 오후 11 55 05](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/47974623/4164f041-5b2d-4ecb-be25-bfa95da0452c)

공연 및 전시 도메인 특성상, 하나의 전시가 진행되는 시간대에 따라 여러 예약이 가능할 것이라 생각했습니다.
때문에 전시행위(perfrom)를 entity로 식별하여 perfrom : Performance = N : 1 의 관계로 개발을 진행했습니다.

## 2.전시 및 공연에 다양한 할인정책 적용
![스크린샷 2024-01-31 오후 11 57 21](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/47974623/20f38815-6700-4005-9539-46af3d868d7f)

추상클래스를 이용하여 새로운 할인정책의 등장으로부터 OCP원칙을 지키도록 구성하였습니다.

## 3. 도메인간 결합

![스크린샷 2024-02-01 오전 12 13 37](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/47974623/526843b0-5569-4e7e-9844-edff39a2f7a6)

예약기능을 개발하면서 예약도메인과 전시도메인이 강하게 결합되는 것을 식별했습니다.

![스크린샷 2024-02-01 오전 12 15 48](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/47974623/689db154-1152-4996-8bab-b05d14aa64eb)

도메인간의 결합도를 낮추기 위한  이벤트 혹은 메시징큐를 쉽게 도입할 수 있도록 구조를 변경했습니다.
실제 코드에선 이벤트를 발생시키지 않고 구조만 반영한 후 마무리했습니다.

## 4.예약 취소시 알림 기능, 비동기 이벤트 발행
예약 취소로직이 알림의 성공/실패 여부에 영향을 받으면 안되므로 비동기 이벤트로 구성했습니다.

```java
@Test
public void 예약_취소_시_구독자_알림_발송_실패_예약취소에_영향_없음() throws InterruptedException {
	Mockito.doThrow(new RuntimeException("알림 외부서비스 에러")).when(notificationOutput).reservationCancelNotify(any(),any());
	...
	reservationService.cancel(cancelRequest);

	ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
	executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);

	...
	assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCEL);
	
```

테스트는 mokito를 이용하여 예외를 발생시키고, 비동기 로직을 진행하는 스레드가 끝날때까지 대기한 뒤 취소되었는지 확인하도록 구현했습니다.


# 이후 개선사항
1. 테스트 코드를 꼼꼼히 작성하지 못했습니다. 
2. Controller 구현/테스트를 작성하지 않았습니다. 
3. 할인정책 CURD가 구현되지 않았습니다.
4. 부적절한 네이밍이 존재합니다.
5. 가독성이 좋지 않은 메소드가 있습니다.
