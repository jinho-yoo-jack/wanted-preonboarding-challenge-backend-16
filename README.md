# wanted-preonboarding-challenge-backend-16
원티드 프리온보딩 백엔드 챌린지 사전과제

### 버전 정보
- Spring Boot; 3.2.1
- H2 DB 

## 과제1 - 필수
- 고객의 요구사항: 빠르게 변화는 고객의 니즈를 빠른 시간 내에 적용할 수 있는 Wanted Ticket 서비스를 아래와 같이 개발 해주세요.
- 비즈니스 요구사항:
  - [X] 예약 시스템
    - 요청 : POST /api/v1/reserves
    - Request Message: 고객의 이름, 휴대 전화, 결제 가능한 금액(잔고), 예약을 원하는 공연 또는 전시회ID, 회차, 좌석 정보
    - Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    - 특이사항: 예약 결제 시, 다양한 할인 정책이 적용될 수 있음.
  - [] 예약 조회 시스템
    - Request Message: 고객의 이름, 휴대 전화
    - Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
  - [X] 공연 및 전시 정보 조회(목록, 상세 조회)
    - 목록 조회 : GET /api/v1/performances?isReserve={value} DEFAULT enable
    - 상세 조회 : GET /api/v1/performances/{performanceId}
    - Request Message: 예매 가능 여부
    - Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
  - [ ] 예약 가능 알림 서비스
    - 특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
    - Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보