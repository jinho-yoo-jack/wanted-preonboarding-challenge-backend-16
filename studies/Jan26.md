# 1월 26일 구현 사항

## 알림 기능
- 알림을 위해서 twilio라이브러리를 사용할 것이다. 
- 알림을 주어야 하는 `공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보` 데이터는 어디서 가져올 수 있을까?
  - `공연ID, 공연명, 회차, 시작 일시` : 공연 객체
  - `예매 가능한 좌석 정보` : 예약에서 가져올 수 있다
    - 예매가 불가능한 사람들이 예매를 진행할 것이다. 
    - 즉, 좌석 정보 전체를 순회해 데이터를 가져오는 것이 아닌 Reservation객체의 SeatInfo를 가져오면 될 것 같다는 생각이 든다. 
- 어떻게 대기자를 저장할 것인가?
  - 예약 대기자 저장 방식
    - Map을 이용해 저장할 계획이다. 
      - `Map<PerformanceInfo, List<UserInfo>>`로 데이터를 담는다
        - PerformanceId, PerformanceName, Round가 지속적으로 같이 붙어다녀, 캡슐화를 시키는 것이 효율적이라 판단했다. 
      - 이벤트로 `SeatInfo, Performance`를 담아서 온다면, `List<UserInfo>`의 전화번호에 PerformanceInfo, SeatInfo를 보내줄 수 있다고 생각한다.
- 어떻게 동작시킬까?
  - 예약 취소시 이벤트로 동작시킨다.
    - 예약 취소 비즈니스 로직에 Reservation, 그리고 그 속에 SeatInfo가 존재한다. 
    - SeatInfo, Performance를 AlarmSender에 이벤트 메시지로 전달해준다. 
    - AlarmSender에서 AwaitingReservation을 Performance.getId()를 통해서 조회한다. 
    - 조회한 AwaitingReservation.getUserInfo().getPhone()을 통해 전화번호를 받아온다. 
    - 받아온 전화번호들에 Twilio를 통해 메시지를 전송시킨다. 