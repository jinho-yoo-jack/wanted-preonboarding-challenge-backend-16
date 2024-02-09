# 구현 내역
- 예약 시스템
- 예약 조회 시스템
- 공연 및 전시 정보 조회(목록, 상세 조회)

# 미구현 내역
- 예약 가능 알림 서비스
- 어플리케이션 전체에 걸친 Exception 처리

## 차후 보완할 점
- 예약 가능 알림 서비스 구현
- Exception Handling
- Request Value Validation
- URL 및 객체 네이밍 정리
- 테스트 코드 사용

## 후기
화면을 만들어버리는 바람에 시간을 많이 소요하였습니다. 또한 어플리케이션 전체에 대한 설계를 꼼꼼히 하지 않고 구현에 들어갔기 때문에 중간에 수정하느랴 걸린 시간도 많았습니다.

기능 하나를 구현할 때마다 커밋을 했어야 했는데 기능 여러개를 만든 뒤 히스토리를 찾아가며 커밋을 했던 점은 반성할 점으로 꼽고자 합니다.

개인적인 일 때문에 처음부터 시간을 많이 쏟지 못할 것을 예상했기 때문에 Request 값을 넣었을 때 작동하도록만 만들자는 목표로 만든 점이 아쉽습니다.

# wanted-preonboarding-challenge-backend-16
원티드 프리온보딩 백엔드 챌린지 사전과제

## 프로젝트 필수 패키지 및 버전 정보
### 버전 정보
- Spring Boot; 3.2.1
- JDK(Java Development Kit); Java 17 이상
  - Intellij에서 JDK 버전 변경하는 방법(참고 URL: https://inpa.tistory.com/entry/IntelliJ-%F0%9F%92%BD-%EC%9E%90%EB%B0%94-JDK-%EB%B2%84%EC%A0%84-%EB%B3%80%EA%B2%BD-%EB%B0%A9%EB%B2%95)
### 필수 설치 패키지
- Docker Desktop

## 과제1 - 필수
- 고객의 요구사항: 빠르게 변화는 고객의 니즈를 빠른 시간 내에 적용할 수 있는 Wanted Ticket 서비스를 아래와 같이 개발 해주세요.
- 비즈니스 요구사항:
  - 예약 시스템
    - Request Message: 고객의 이름, 휴대 전화, 결제 가능한 금액(잔고), 예약을 원하는 공연 또는 전시회ID, 회차, 좌석 정보
    - Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    - 특이사항: 예약 결제 시, 다양한 할인 정책이 적용될 수 있음.
  - 예약 조회 시스템
    - Request Message: 고객의 이름, 휴대 전화
    - Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
  - 공연 및 전시 정보 조회(목록, 상세 조회)
    - Request Message: 예매 가능 여부
    - Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
  - 예약 가능 알림 서비스
    - 특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
    - Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보 
    
- 기초 과제
  1. 위에서 이야기 하고 있는 비즈니스 요구사항을 만족하는 Application을 만들어주세요.
     - 개발 실력 브실골 이하: 기존에 작성 되어 있는 코드를 삭제 하지 않고! 기존 코드 위에 코드를 작성해주세요!
     - 다이아 이상: ticket 패키지를 삭제하고 새롭게 패키지를 만든 후에 Application을 개발해주세요!
  2. 데이터베이스는 MySQL를 사용 해주시고 프로젝트 내에 docker-compose.yml를 이용하여 MySQL 서비스를 Running 시키면 됩니다.
     - DB의 데이터를 저장하는 경로(`/Users/black/dev/mysql_docker/data`)와 DB 초기화 쿼리 파일의 경로(`/Users/black/IdeaProjects/backend-preonboarding/src/main/resources/initd`)는 개인의 환경에 맞게 수정 해주세요!
  3. 스키마 정보 및 테스트 데이터는 "./resources/initdb"에 디렉토리에 *.sql 확장자로 정의 되어 있습니다. Application의 DTO와 Entity를 구성할 때 참고 해주세요!
     - Application 개발 중 새로운 테이블이 필요하다면 테이블을 추가는 마음대로 하셔도 됩니다.
- 심화 과제
  1. Application을 모두 작성 했다면! 코드를 읽는 동료가 코드를 쉽게 이해할 수 있도록 코드를 리팩토링 해주세요!
  2. 리팩토링을 진행할 때는! 기술 위주가 아니라! "변경"에 쉽게 적응할 수 있는 코드 구조로 리팩토링 해주세요!

### 환경 설정과 Github에 대한 궁금증이 있다면! Issues에 등록해주시면 답변 드리겠습니다.
- https://github.com/jinho-yoo-jack/wanted-preonboarding-challenge-backend-16/issues
- 참고 URL: https://devlog-wjdrbs96.tistory.com/227
       

## 과제2 - 선택사항
- 다른 사람의 PR 염탐하기!
- **백문이 불여일견**이라고 했습니다! 다른 사람의 코드를 보고 궁금한 점이나 코드 작성의 의도를 물어봐 주세요! 또는 코드에 대해서 자신의 생각을 코멘트로 달아주세요! 
- 코드 리뷰를 통해서 내가 놓친 부분이나 미처 생각 하지 못했던 부분을 깨닫을 수 있고, 좋은 코드와 나쁜 코드가 무엇인지 경험할 수 있습니다!
- PR에 코멘트를 달아도! 잔디가 심어진다는 사실 알고 계신가요?!!!!
- 과제2의 베네핏
  1. 질 높은 코드 리뷰를 진행 주신 분들은 수업의 마지막 날에 진행 되는 커피챗 및 맥주챗 뽑기 진행 시, 이름을 3개 더 넣어 드리도록 하겠습니다.
  2. 열심히 코드 리뷰 활동을 하시는 분의 PR은 제가 무조건 처음부터 끝까지 코드 리뷰를 진행하도록 하겠습니다!!

## 과제 제출 방법
1. master 브랜치로 프로젝트를 clone 해주세요.
2. 로컬 레파지토리에 `feature/이름or닉네임`으로 피쳐 브랜치를 생성해주세요.
4. 이제부터 사전과제를 풀어봅니다.
5. 모든 사전과제를 풀었다면 이제 원격 레파지토리에 commit 및 push 후 PR을 올려 해주세요.
6. Example
   ```shell
    1. git checkout -b feature/migration2TcAsMasterSlaveStructure
    # 브랜치를 생성하고 해당 브랜치로 checkout.
    # feature의 브랜치명은 내부적으로 관리하는 issues번호나 프로젝트 관리도구에의 ID 값을 이용
    2. git add .
    # 모든 변경사항을 tracking 되는 상태로 변경할거에요.
    3. git commit -m “Commit Messsage for Modify Information”
    # git commit를 수행하고 작업내용을 팀 내부 규약대로 작성
    3-1. git push --set-upstream origin feature/migration2TcAsMasterSlaveStructure
    # 해당 내용을 remote repository로 push
    4. push하면 gitlab/github에 Pull Request나 Merge Request 생성하는 버튼이 활성화
    5. Merge Request 버튼을 클릭 후, 숙제에 관한 내용 작성 후 PR 요청을 보내면 끝
    6. Merge 승인이되면 WEB UI화면에서 merge 버튼이 활성화 됨.
   ```