# 원티드 프리온보딩 BE 2월 - 배지수 사전 과제


## 프로젝트 필수 패키지 및 버전 정보
### 버전 정보
- Spring Boot; 3.2.1
- JDK(Java Development Kit); Java 17
### 필수 설치 패키지
- Docker Desktop

## 기능
- 공연/전시
  - 전체 공연 조회 `GET` /performance
  - 예매 가능한 공연 조회 `GET` /performance/enable
- 예매
  - 공연/전시 예약 `POST` /reservation
    - 예약된 좌석은 상태가 disable 로 변경
    - 해당 공연의 모든 좌석이 disable 상태면 공연/전시 상태도 disable로 변경
  - 특정 유저의 예약 내역 조회 `GET` /reservation
  - 예약 취소 `DELETE` /reservation
    - 취소일이 공연 시작일 이후일 경우, 취소 불가
    - 취소 시, 좌석 상태 enable 로 변경
    - 매진이었던 공연의 경우, 아직 공연 시작일까지 날짜가 남았고 & 예매 가능 좌석이 남아있으면 공연/전시 상태도 enable로 변경
    - 해당 공연 취소 대기 알림 신청자에게 메일 발송
- 알림
  - 알림 등록 `POST` /alarm

### 예외
- PerformanceNotFound : 공연 이름, 회차로 조회되는 공연이 없는 경우 발생
- PerformanceDisable : 공연 예매 상태가 disable 일 경우 발생
- PerformanceSeatNotFound : 공연 ID, 회차, 열, 좌석으로 조회되는 좌석이 없는 경우 발생
- PerformanceSeatDisable : 좌석 예매 상태가 disable 일 경우 발생
- PriceOver : 티켓 가격이 지불 가능 금액보다 많을 경우 발생
- ReservationNotFound : 예약 ID로 조회되는 예약 내역이 없는 경우 발생
- InvalidReservation : 예약 ID로 조회되는 예약 내역과, 예약 취소 요청에서 받아온 유저 정보 혹은 공연 정보가 다를 경우 발생
- AlarmDuplicated : 유저 1명당 1개 공연에 1회 알림 신청만 할 수 있다고 가정, 1회 이상 알림 신청을 시도할 경우 발생
- CancelTimeOver : 예매 취소 시, 공연 시작일이 이미 지났을 경우 발생

## 데이터 베이스 설계
![image](https://github.com/geesuee/wanted-preonboarding-challenge-backend-16/assets/68639271/a9811b9f-a1f2-4f66-8475-3bc1f3da3e38)
- 유저 정보를 `User` 테이블로 분리, 기존 테이블에 있던 name, phone_number 컬럼을 user_id로 대체했습니다.
- 예매 취소가 발생하여 `Reservation` 테이블에서 데이터가 삭제되면, 해당 데이터가 `CanceledReservation` 테이블에 적재되도록 trigger를 추가했습니다.
- 취소 대기 알람 신청 내역을 적재할 `Alarm` 테이블을 추가했습니다.