# 개발자 키우기 프로젝트
본 포스트는 패스트캠퍼스 강의 프로젝트를 만든것이다
> # 개발자키우기 환경설정  
Language: Java(11)  
Project : Gradle Project  
Spring boot: 2.6.7  
Dependencies: Lombok  
Spring Web  
H2 Database( 로컬 H2데이터베이스로 DB를 만드는 방법을 알아보자)  
Spring Data JPA (H2데이터베이스에 접근하기 위해 JPA를 사용)  
Validation( Bean Validation을 추가해주는 라이브러리를 추가)  
![image](https://user-images.githubusercontent.com/85244656/166393734-740263da-5698-44b0-96c0-e54b82bb5f87.png)


## 진행상황
* 22/05/01 
  * 단순한 패키지 구조 세팅 및 H2 DB 연결하여 Developer Entity를 이용하여 개발자 생성 코드 작성
  -  데이터 벨리데이션/비지니스 로직 벨리데이션 추가
  -  JPA Repository 적용
* 22/05/02
  - 개발자 생성 (POST) 기능 추가 및 테스트
  - Developer 조회기능(GET) 추가 및 테스트 
  - Devleoper memberId를 이용한 Developer상세정보 조회 기능 추가
  - Developer 상세 기능추가 및 테스트
  - Devleoper 삭제 기능 추가
    -  @Transactional 애노테이션의 기능에 대해서 공부해보기
