### 간단한 연락처 애플리케이션

---

## 개요
이 프로젝트는 간단한 연락처 애플리케이션을 구현합니다. 사용자는 이름과 전화번호를 입력하여 연락처를 추가할 수 있으며, 생일, 성별, 메모 등의 추가 정보를 입력할 수 있습니다.

## 기능

1. **UI 구성**:
   - **이름** (필수): TextField
   - **전화번호** (필수): TextField
   - **이메일** (선택): TextField
   - **더보기 버튼** (클릭 시 확장. 생일, 성별, 메모 폼 등장): Button
     - **생일** (선택): DatePickerDialog
     - **성별** (선택): Radio Button
     - **메모** (선택): TextField

## 사용 방법

1. **연락처 추가하기**:
   - 애플리케이션 실행 후, 연락처 추가 버튼을 클릭합니다.
   - 이름과 전화번호를 입력합니다.
   - 필요 시 '더보기' 버튼을 눌러 생일, 성별, 메모를 입력합니다.
   - 저장 버튼을 클릭하여 연락처를 저장합니다. 저장이 완료되면 토스트 메시지가 표시됩니다.
   - 입력을 취소하려면 취소 버튼을 클릭합니다. 취소 토스트 메시지가 표시됩니다.
