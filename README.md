# wanted-preonboarding-challenge-backend-16
원티드 프리온보딩 백엔드 챌린지 사전과제


## 구현 내용

### endpoint
  - 예약
  - 예약 내역 조회
  - 공연 목록 조회
  - 공연 상세 조회

### database
  - Performance Id 값을 round 필드와 함꼐 EmbeddedId로 잡았습니다. 
  - 할인 정보를 담은 'discount_info' 테이블을 생성했습니다. 할인 금액은 비율 기준으로 정의했습니다.

### key-point
  - 예약 endpoint 유효성 검증에서 결제 금액 검증은 카드 등의 직접 결제 / 할인 결제 2종류로 나뉜다고 가정하고 decorator 패턴으로 구현했습니다.
  - Layered Architecture를 적용해봤습니다.

## 후기
  decorator 패턴 사용이 익숙하지 않았는데, 적절한 예시로 연습해 볼 수 있어서 만족했습니다.
  Layered Architecture를 알고 있었는데 이번에 적용해보니 실무에서 너무 많은 클래스가 존재해서 모듈 구조가 복잡해지는 문제를 어느 정도 해소할 수 있을 것 같습니다.
  물론..너무 많으면 어쩔 수 없지만.. domain과 infrastructer 두 부분으로 나눠놓으니 인터페이스와 구현체 구분이 분명하다는 점에서 만족했습니다.

  예약 가능 알림 구현으로 Observer 패턴도 처음 사용해보는 중인데 지체되어서 아쉽지만 우선 제외하고 pr 올립니다. 
  Observer 완성까지 구현해보는 걸 목표로 하고 있습니다..!  