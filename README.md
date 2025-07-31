# 일정 관리 API

## 프로젝트 개요
일정을 생성, 조회, 수정, 삭제할 수 있는 REST API입니다.

## 기술 스택
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Lombok
- Gradle

## 데이터베이스 설계

### Schedule 테이블
| 컬럼명 | 타입 | 제약조건 | 설명 |
|--------|------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 식별자 |
| name | VARCHAR(255) | NOT NULL | 작성자명 |
| password | VARCHAR(255) | NOT NULL | 비밀번호 |
| title | VARCHAR(500) | NOT NULL | 일정 제목 |
| contents | TEXT | NOT NULL | 일정 내용 |
| created_at | TIMESTAMP | NOT NULL | 작성일 |
| modified_at | TIMESTAMP | NOT NULL | 수정일 |

## API 명세서

### 1. 일정 생성
```
POST /api/schedules
Content-Type: application/json

Request Body:
{
  "name": "홍길동",
  "password": "1234",
  "title": "회의 준비",
  "contents": "월요일 오전 회의 자료 준비하기"
}

Response:
{
  "id": 1,
  "title": "회의 준비",
  "contents": "월요일 오전 회의 자료 준비하기"
}
```

### 2. 일정 전체 조회
```
GET /api/schedules

Response:
[
  {
    "id": 1,
    "name": "홍길동",
    "title": "회의 준비",
    "contents": "월요일 오전 회의 자료 준비하기",
    "createdAt": "2025-01-31T10:30:00",
    "modifiedAt": "2025-01-31T10:30:00"
  }
]
```

### 3. 일정 단건 조회
```
GET /api/schedules/{id}

Response:
{
  "id": 1,
  "name": "홍길동",
  "title": "회의 준비",
  "contents": "월요일 오전 회의 자료 준비하기",
  "createdAt": "2025-01-31T10:30:00",
  "modifiedAt": "2025-01-31T10:30:00"
}

Error Response:
404 Not Found - 존재하지 않는 일정
```

### 4. 일정 전체 수정
```
PUT /api/schedules/{id}
Content-Type: application/json

Request Body:
{
  "password": "1234",
  "name": "홍길동",
  "title": "회의 준비 완료",
  "contents": "월요일 오전 회의 자료 준비 완료함"
}

Response:
{
  "id": 1,
  "name": "홍길동",
  "title": "회의 준비 완료",
  "contents": "월요일 오전 회의 자료 준비 완료함",
  "createdAt": "2025-01-31T10:30:00",
  "modifiedAt": "2025-01-31T15:20:00"
}

Error Response:
404 Not Found - 존재하지 않는 일정
401 Unauthorized - 비밀번호 불일치
```

### 5. 일정 제목 수정
```
PATCH /api/schedules/{id}
Content-Type: application/json

Request Body:
{
  "password": "1234",
  "title": "회의 준비 완료"
}

Response:
{
  "id": 1,
  "name": "홍길동",
  "title": "회의 준비 완료",
  "contents": "월요일 오전 회의 자료 준비하기",
  "createdAt": "2025-01-31T10:30:00",
  "modifiedAt": "2025-01-31T15:20:00"
}

Error Response:
404 Not Found - 존재하지 않는 일정
401 Unauthorized - 비밀번호 불일치
```

### 6. 일정 삭제
```
DELETE /api/schedules/{id}
Content-Type: application/json

Request Body:
{
  "password": "1234"
}

Response:
204 No Content

Error Response:
404 Not Found - 존재하지 않는 일정
401 Unauthorized - 비밀번호 불일치
```

## 실행 방법

### 1. 데이터베이스 설정
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/schedule_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. API 테스트
- 기본 포트: `http://localhost:8080`
- Postman, Insomnia 등의 도구로 테스트 가능

## 주요 기능
- ✅ 일정 CRUD 기능
- ✅ 비밀번호 기반 인증
- ✅ JPA Auditing을 통한 자동 시간 관리
- ✅ JSON 기반 REST API
- ✅ 예외 처리 및 적절한 HTTP 상태 코드 반환

## 보안 고려사항
- 비밀번호는 응답 데이터에 포함되지 않음
- 수정/삭제 시 비밀번호 검증 필수
- 존재하지 않는 리소스에 대한 404 처리