# wanted-preonboarding-challenge-backend-16
원티드 프리온보딩 백엔드 챌린지 사전과제

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
  3. 스키마 정보 및 테스트 데이터는 "./resources/initdb"에 디렉토리에 *.sql 확장자로 정의 되어 있습니다. Application의 DTO와 Entity를 구성할 때 참고 해주세요!
     - Application 개발 중 새로운 테이블이 필요하다면 테이블을 추가는 마음대로 하셔도 됩니다.
- 심화 과제
  1. Application을 모두 작성 했다면! 코드를 읽는 동료가 코드를 쉽게 이해할 수 있도록 코드를 리팩토링 해주세요!
  2. 리팩토링을 진행할 때는! 기술 위주가 아니라! "변경"에 쉽게 적응할 수 있는 코드 구조로 리팩토링 해주세요!
       

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