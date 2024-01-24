# 1월 24일 학습 내용

## DDD의 디렉토리 구조

DDD의 디렉토리 구조를 전체적으로 공부해보기로 했다. 

Domain
---
- Entities<br> : 핵심 개념의 객체
- ValueObjects<br> : 불변객체
- Aggregates<br> : 연관된 다른 엔티티나 객체를 그룹화시켜둔다.
- Repositories<br> : 어그리게이트 저장 및 조회 인터페이스

이렇게 보았을 때, 무슨 의미인지 해석하기 힘들었다. 
어제 정리한 구조에 맞게, 각 엔티티의 예시를 들어보았다.

- Entities : Member
- ValueObjects : Account
- Aggregates : Customer(Member & Account)
- Repositories : MemberRepository

Application
---
- UseCases : 비즈니스 규칙을 구현하는 서비스 계층
- Services : 도메인 계층을 사용하여 응용 로직을 처리하는 서비스들

서비스는 항상 사용하던 대로 리포지토리를 불러와 비즈니스 로직을 처리한다고 생각했다. 
그렇다면 UseCases는 무엇을 둘 수 있을까? 
여기에서 새로운 방식의 코드를 넣어보려고 한다. 
Event기반으로 일을 처리하게 하려고 한다. 
여기에서는 Member가 이벤트 리스닝을 통해 동작을 하지 않기 때문에, 
좌석을 예시로 들어보아야겠다(코드는 아직 학습을 해보지 않아 작성하지 않고, 한글로 적어보았다.)

`
예약 취소 이벤트 : 좌석을 추가해준다. 공연을 예약가능하게 수정 이벤트를 발생시킨다.
예약 진행 이벤트 : 좌석을 삭제해준다. 공연의 남은 좌석 확인 이벤트를 발생시킨다.
`

자바스프링을 공부할 때, kafka라는 것을 들어보았다. 
카프카는 이벤트 스트리밍 플랫폼으로 이벤트의 안정적 확산 및 전파를 도와준다고 한다. 
시간이 된다면 이 부분도 적용해보고 싶다.

Infrastructure
---
- Persistence : 데이터베이스와 관련된 코드, ORM을 사용하는 경우 리포지토리의 일부가 된다고 한다
- ExternalServices : 외부 시스템과 통신하는 코드
- Configuration<br> : 프로젝트 설정과 관련된 코드

ExternalServices는 무엇을 써야할지 너무 확실히 보였다.
알림 기능을 처리하기 위해서 써야겠다는 생각이 들었다. 
그런데, Persistence가 모호했다. 
데이터베이스와 관련된 코드라고 하는데, Repository와의 차이를 아직 모르겠다. 


Interface
---
- API : 외부와 통신하는 API 코드
- Web : 웹 애플리케이션과 관련된 코드
- UI : 사용자 인터페이스와 관련된 코드

여기에서 느낀건, 결국 백엔드는 Controller만이 존재할 수 있겠다는 생각이 든다. 


Common
---
- SharedKernel : 여러 도메인에서 공유하는 중요한 핵심 요소들

Common부분에서는 공통적으로 extends시킬 DefaultEntity를 넣고싶다는 생각이 들었다. 
createdAt, updatedAt을 넣어서 처리해야겠다는 생각이 든다. 

## 기존 플로우 수정하기

요구사항을 다시 훑어보고, initdb의 쿼리문들을 읽어보았을 때 기존 플로우에서 다듬어야 할 사항들을 생각했다. 

- PerformanceSeatInfo객체
  - 해당 객체를 삭제/추가하는 방식이 아닌, `is_reserved`라는 컬럼은 `enable`, `disable`로 바꾼다. 
  - 이는 데이터베이스가 삭제하는 방식의 특이점 때문이라고 생각한다. 
    - 데이터베이스는 삭제를 할 때 실제로 공간을 비우는 것이 아니다. 
    - 공간을 넘기고, 가장 마지막 공간에 데이터를 추가 하고 해당 데이터의 주소를 인덱스에 담아주는 방식이다. 
    - 이랬을 때, `enable/disable`을 수정하는 것이 더 낫다고 생각한다. 

- Reservation 객체
  - Reservation객체를 만들어 저장하는 방식으로 데이터를 처리하는 것이 요구사항인것이다. 
  - 어떻게 보면, PerformanceSeatInfo의 round, gate, line, seat을 가지고 있어야 하는 구조다. 
  - `round, gate, line, seat`를 담는 ValueObject를 사용해서 데이터를 처리해야겠다는 생각이 든다. 

궁극적으로 이전의 워크플로우에서 수정사항이 생겨 이를 피그마를 이용해 다시 그려보았다.
![스크린샷 2024-01-24 19 13 23](https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/assets/99702271/5513ed95-4efe-4a30-9872-56de38e107ca)

