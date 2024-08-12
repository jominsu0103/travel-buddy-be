# 기술 스택
### 버전관리
![image](https://camo.githubusercontent.com/ccbdc29329afff39a4b077da431827477c1c0b3b8546e2ec570e8acd88bcc0fb/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f6769742d4630353033323f7374796c653d666f722d7468652d6261646765266c6f676f3d676974266c6f676f436f6c6f723d7768697465)
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

### 사용 언어
<img src="https://img.shields.io/badge/java 17-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">

### 빌드 도구
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white">

### 데이터베이스
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white">
<img src="https://img.shields.io/badge/mongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white">

### 서버
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white">
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">
<img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white">
<img src="https://img.shields.io/badge/AMAZON RDS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">

# 프로젝트 진행 순서
#### 1주차 : 기획 및 역할분담, ERD작성
#### 2주차 : API 구현
#### 3주차 : API 구현, 서버 배포
#### 4주차 : API 구현, 테스트 및 버그 수정


# 역할 분담 및 개발전략
- 와이어프레임과 요구사항을 기반으로 ERD를 먼저 작성 후 API 개발
- 1, 2차 프로젝트에서 맡았던 기능 고려한 API 배정
- 각자의 branch에 push후에 pr을 통해서 main으로 merge
- 배포서버 기준으로 bug test 및 fix

## 김찬호 (feat/chat)
- 실시간 채팅 / 채팅목록
- 댓글 조회, 등록, 수정, 삭제
- 좋아요 등록, 취소

## 조민수 (feat/board)
- 게시글 조회, 등록, 수정, 삭제
- 게시글 필터링
- EC2, S3 세팅
- HTTPS 통신 세팅

## 최호현 (feat/route)
- 내경로 조회, 등록, 수정, 삭제
- RDS 세팅

## 황혜영 (feat/auth)
- SMS전송 활용 회원가입
- 소셜로그인
- 스프링시큐리티, JWT를 통한 인증, 인가
- 회원정보(비밀번호, 프로필이미지) 변경

# 앞으로의 추가 과제
> [IMPORTANT]
> - 댓글 / 좋아요 등록시 SSE 활용 실시간 알림 구현
> - 채팅목록 조회 버그 수정
> - 채팅목록 조회시 유저 실시간 접속여부 구현


