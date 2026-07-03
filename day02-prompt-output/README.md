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

### 4. Zero-shot vs Few-shot 비교 시뮬레이션 페이지 (`/zeroVSfewshot`)
프롬프팅 방식(Zero-shot과 Few-shot)의 차이를 시각적으로 비교하고 직접 테스트할 수 있는 인터랙티브 웹 페이지입니다.
- `/zeroVSfewshot`: 비교 페이지 UI를 제공하는 HTML 페이지
- `/api/simulate/zero-shot`: 사용자 입력을 받아 Zero-shot 방식으로 감성/리뷰 키워드를 추출하는 API
- `/api/simulate/few-shot`: 사용자 입력과 예시(Few-shot)를 함께 전달하여 정형화된 포맷으로 리뷰 정보를 추출하는 API

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

#### 5. Zero-shot vs Few-shot 시뮬레이션 웹페이지 & API 테스트
브라우저에서 직접 인터랙티브 UI를 통해 테스트하거나 API를 호출해 볼 수 있습니다.

**페이지 접속**
브라우저에서 `http://localhost:8080/zeroVSfewshot` 에 접속하여 프롬프팅 차이를 시뮬레이션 해보세요.

**Zero-shot API 호출 테스트**
```bash
curl -X GET "http://localhost:8080/api/simulate/zero-shot?text=음식이 늦게 나오고 너무 짜서 실망했어요."
```

**Few-shot API 호출 테스트**
```bash
curl -X GET "http://localhost:8080/api/simulate/few-shot?text=음식이 늦게 나오고 너무 짜서 실망했어요."
```
*Few-shot API의 경우 요구한 `[장점, 단점, 별점]` 포맷으로 일정하게 출력되는 것을 확인할 수 있습니다.*
