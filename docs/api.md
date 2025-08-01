# Sparta Schedules API 명세서

스파르타 일정 관리 시스템의 REST API 문서입니다.

## Base URL
```
http://localhost:8080
```

## API 엔드포인트

### 📝 일정 관리

#### 1. 일정 생성
새로운 일정을 생성합니다.

**POST** `/schedules`

**Request Body:**
```json
{
    "name": "홍길동",
    "password": "mypassword123", 
    "title": "팀 회의",
    "content": "주간 팀 회의 및 프로젝트 진행상황 공유"
}
```

**Response:** `201 Created`
```json
{
    "name": "홍길동",
    "title": "팀 회의",
    "content": "주간 팀 회의 및 프로젝트 진행상황 공유",
    "createdAt": "2025-08-01T09:30:00.123456",
    "modifiedAt": "2025-08-01T09:30:00.123456"
}
```

---

#### 2. 일정 조회
모든 일정을 조회하거나 특정 작성자의 일정을 조회합니다.

**GET** `/schedules?name={작성자명}`

**Query Parameters:**
- `name` (optional): 조회할 작성자명 (비어있으면 전체 일정 조회)

**Examples:**
```
GET /schedules              # 전체 일정 조회
GET /schedules?name=홍길동   # 홍길동이 작성한 일정만 조회
```

**Response:** `200 OK`
```json
[
    {
        "name": "홍길동",
        "title": "팀 회의",
        "content": "주간 팀 회의 및 프로젝트 진행상황 공유",
        "createdAt": "2025-08-01T09:30:00.123456",
        "modifiedAt": "2025-08-01T09:30:00.123456"
    },
    {
        "name": "김철수",
        "title": "프로젝트 리뷰",
        "content": "1차 프로젝트 결과 검토 및 피드백 진행",
        "createdAt": "2025-08-01T14:20:00.654321",
        "modifiedAt": "2025-08-01T14:20:00.654321"
    },
    {
        "name": "이영희",
        "title": "기획 회의",
        "content": "신규 기능 기획안 검토 및 일정 논의",
        "createdAt": "2025-08-01T16:45:00.789012",
        "modifiedAt": "2025-08-01T16:45:00.789012"
    }
]
```

**Response:** `200 OK`

---

#### 3. 일정 단건 조회
특정 ID의 일정을 조회합니다.

**GET** `/schedules/{id}`

**Path Parameters:**
- `id`: 일정 ID

**Example:**
```
GET /schedules/1
```

**Response:** `200 OK`
```json
{
    "name": "홍길동",
    "title": "팀 회의",
    "content": "주간 팀 회의 및 프로젝트 진행상황 공유",
    "createdAt": "2025-08-01T09:30:00.123456",
    "modifiedAt": "2025-08-01T09:30:00.123456"
}
```

---

#### 4. 일정 수정
일정의 작성자명과 제목을 수정합니다.

**PATCH** `/schedules/{id}`

**Path Parameters:**
- `id`: 수정할 일정 ID

**Request Body:**
```json
{
    "name": "김철수",
    "title": "개발팀 미팅",
    "password": "mypassword123"
}
```

**Response:** `200 OK`
```json
{
    "name": "김철수",
    "title": "개발팀 미팅",
    "content": "주간 팀 회의 및 프로젝트 진행상황 공유",
    "createdAt": "2025-08-01T09:30:00.123456",
    "modifiedAt": "2025-08-01T16:45:00.654321"
}
```

---

#### 5. 일정 삭제
특정 일정을 삭제합니다. 비밀번호 확인이 필요합니다.

**DELETE** `/schedules/{id}`

**Path Parameters:**
- `id`: 삭제할 일정 ID

**Request Body:**
```json
{
    "password": "mypassword123"
}
```

**Response:** `204 No Content`

---

### 💬 댓글 관리

#### 6. 댓글 작성
특정 일정에 댓글을 작성합니다.

**POST** `/schedules/{id}/comments`

**Path Parameters:**
- `id`: 댓글을 작성할 일정 ID

**Request Body:**
```json
{
    "name": "이영희",
    "password": "commentpass456", 
    "content": "좋은 아이디어네요! 참석하겠습니다."
}
```

**Response:** `201 Created`
```json
{
    "id": 1,
    "name": "이영희",
    "content": "좋은 아이디어네요! 참석하겠습니다.",
    "createdAt": "2024-01-15T10:15:00",
    "updatedAt": "2024-01-15T10:15:00"
}
```

---

#### 7. 일정과 댓글 함께 조회
특정 일정의 상세정보와 모든 댓글을 함께 조회합니다.

**GET** `/schedules/{id}/with-comments`

**Path Parameters:**
- `id`: 조회할 일정 ID

**Example:**
```
GET /schedules/1/with-comments
```

**Response:** `200 OK`
```json
{
    "id": 1,
    "name": "홍길동",
    "title": "팀 회의",
    "content": "주간 팀 회의 및 프로젝트 진행상황 공유",
    "createdAt": "2024-01-15T09:30:00",
    "updatedAt": "2024-01-15T09:30:00",
    "comments": [
        {
            "id": 1,
            "name": "이영희",
            "content": "좋은 아이디어네요! 참석하겠습니다.",
            "createdAt": "2024-01-15T10:15:00",
            "updatedAt": "2024-01-15T10:15:00"
        },
        {
            "id": 2,
            "name": "박민수",
            "content": "시간 조정이 가능한지 확인해주세요.",
            "createdAt": "2024-01-15T11:20:00",
            "updatedAt": "2024-01-15T11:20:00"
        }
    ]
}
```

---

## 응답 형식

모든 API 응답은 JSON 형식으로 반환됩니다.

### 성공 응답
- `200 OK`: 요청 성공
- `201 Created`: 리소스 생성 성공
- `204 No Content`: 삭제 성공

### 에러 응답
- `400 Bad Request`: 잘못된 요청
- `401 Unauthorized`: 비밀번호 불일치
- `404 Not Found`: 리소스를 찾을 수 없음
- `500 Internal Server Error`: 서버 내부 오류

## 주의사항

- 모든 수정 및 삭제 작업에는 비밀번호 확인이 필요합니다.
- 비밀번호는 평문으로 전송되므로 실제 운영 환경에서는 HTTPS를 사용해야 합니다.
- 일정 조회 시 name 파라미터가 없으면 전체 일정을, 있으면 해당 작성자의 일정만 조회합니다.