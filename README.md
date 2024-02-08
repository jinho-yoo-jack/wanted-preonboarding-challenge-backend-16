# 🎨 공연/전시 티켓 예매
- 객체지향 스터디, 기능 구현 프로젝트

## 구현 API
| API 명                | Method | Path | 설명 |
|----------------------|------|------|--|
| 예약 신청 API            | POST | /api/reservations |  |
| 예약 취소 API            | PATCH | /api/reservations/{id}/cancel |  |
| 예약 목록 조회 API         | GET | /api/reservations |  |
| 공연 및 전시 정보 목록 조회 API | GET | /api/performances |  |
| 공연 및 전시 정보 상세 조회 API | GET | /api/performances/{id} |  |

- 참고) 예약 취소 시 잔여석 알림 기능 추가


## 작업
- API 5개 개발
- service 테스트 코드 작업

## 프로젝트 구조
```html
├── src
│ ├── main
│ │ ├── java
│ │ │ └── com
│ │ │     └── wanted
│ │ │         └── preonboarding
│ │ │             ├── TicketApplication.java
│ │ │             ├── core
│ │ │             │ ├── code
│ │ │             │ │ ├── ErrorMessage.java
│ │ │             │ │ ├── NoticeType.java
│ │ │             │ │ └── ReservationStatus.java
│ │ │             │ ├── config
│ │ │             │ │ └── JpaConfiguration.java
│ │ │             │ └── domain
│ │ │             │     └── response
│ │ │             │         └── ResponseHandler.java
│ │ │             └── ticket
│ │ │                 ├── controller
│ │ │                 │ ├── PerformanceController.java
│ │ │                 │ ├── ReservationController.java
│ │ │                 │ └── model
│ │ │                 │     ├── PerformanceInfoModel.java
│ │ │                 │     ├── PerformanceSelectModel.java
│ │ │                 │     ├── ReservationApplyModel.java
│ │ │                 │     ├── TicketHolderModel.java
│ │ │                 │     └── request
│ │ │                 │         └── ReservationApplyRequest.java
│ │ │                 ├── domain
│ │ │                 │ ├── Notice.java
│ │ │                 │ ├── Performance.java
│ │ │                 │ ├── PerformanceSeat.java
│ │ │                 │ ├── Reservation.java
│ │ │                 │ └── base
│ │ │                 │     └── BaseEntity.java
│ │ │                 ├── repository
│ │ │                 │ ├── NoticeRepository.java
│ │ │                 │ ├── PerformanceRepository.java
│ │ │                 │ ├── PerformanceSeatRepository.java
│ │ │                 │ └── ReservationRepository.java
│ │ │                 └── service
│ │ │                     ├── PerformanceService.java
│ │ │                     ├── ReservationService.java
│ │ │                     └── discount
│ │ │                         ├── DiscountPolicy.java
│ │ │                         └── FixDiscount.java
│ │ └── resources
│ │     ├── application.yml
│ │     └── initdb
│ │         ├── create_schema.sql
│ │         └── insert_test_data.sql
│ └── test
│     └── java
│         └── com
│             └── wanted
│                 └── preonboarding
│                     └── ticket
│                         └── service
│                             ├── PerformanceServiceTest.java
│                             └── ReservationServiceTest.java

```
