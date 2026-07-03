# Spring AI Day 2: Prompt & Structured Output


## 기능 설명

### 1. 요약 API (`/api/summary`)
사용자의 문의 내용을 특정 대상이 읽기 좋게 요약합니다. Prompt Template을 사용하여 파라미터를 안전하게 바인딩합니다.

### 2. 분류 API (`/api/classify/raw` 및 `/api/classify`)
고객의 문의를 카테고리, 중요도, 이유로 분류합니다.
- `/api/classify/raw`: 결과를 일반 문자열(String)로 반환합니다.
- `/api/classify`: 결과를 `InquiryResult` 객체(JSON)로 구조화하여 반환합니다.

### 3. 프롬프트 추천 API (`/api/prompts`)
사용자가 원하는 목적을 달성하기 위한 최고의 프롬프트 3가지를 추천해주는 응용 실습 기능입니다. 
- Few-shot 프롬프팅을 적용하여 모델이 정확한 리스트 형태로 응답하도록 유도합니다.
- 객체 리스트 반환을 위해 `ParameterizedTypeReference`를 사용합니다.

---

## 실행 및 테스트 방법

### 프로젝트 실행
1. `day02-prompt-output` 프로젝트 디렉토리로 이동합니다.
2. 터미널에서 다음 명령어를 실행하여 애플리케이션을 구동합니다:
   ```bash
   ./gradlew bootRun
   ```

### API 테스트

브라우저 또는 curl(혹은 Postman)을 사용하여 다음 URL들을 호출해 보세요.

#### 1. 요약 API 테스트
```bash
curl -X GET "http://localhost:8080/api/summary?text=어제 주문한 노트북이 아직 배송 준비중이에요. 금요일까지 꼭 필요합니다.&audience=신입 상담원"
```

#### 2. 분류 API (Raw String) 테스트
```bash
curl -X GET "http://localhost:8080/api/classify/raw?text=화면이 깨져서 왔어요. 환불해주세요!"
```

#### 3. 분류 API (Structured Output - JSON) 테스트
```bash
curl -X GET "http://localhost:8080/api/classify?text=화면이 깨져서 왔어요. 환불해주세요!"
```
*결과가 JSON 형태로 정형화되어 출력되는 것을 확인할 수 있습니다.*

#### 4. 프롬프트 추천 API (객체 리스트 & Few-shot) 테스트
```bash
curl -X GET "http://localhost:8080/api/prompts?purpose=unity mcp 자동화 기획"
```
*결과로 추천된 프롬프트와 추천 이유가 포함된 JSON 리스트가 반환됩니다.*
