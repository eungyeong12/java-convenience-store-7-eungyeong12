# 편의점 결제 시스템

## 🚀️ 기능 목록

1. 상품 목록을 불러온다.
    - 파일 입출력 에러가 발생할 경우 `IllegalArgumentException`을 발생시킨 후 종료한다.

2. 행사 목록을 불러온다.

3. 환영 인사와 함께 상품명, 가격, 프로모션 이름, 재고를 안내하는 메시지를 출력한다.

- 재고가 0개라면 `재고 없음`을 출력한다.

4. 구매할 상품명과 수량을 입력 받는다.

- 입력값이 비어있는 경우 `IllegalArgumentException`을 발생시킨다.
- 상품명, 수량은 하이픈(-)으로, 개별 상품은 대괄호([])로 묶어 쉼표(,)로 구분되어 있지 않은 경우 `IllegalArgumentException`을 발생시킨다.
- 존재하지 않는 상품을 입력하였을 경우 `IllegalArgumentException`을 발생시킨다.
- 구매 수량이 재고 수량을 초과한 경우 `IllegalArgumentException`을 발생시킨다.

5. 상품이 프로모션 적용이 가능한지 확인한다.

6. 프로모션 적용이 불가한 상품은 바로 재고를 차감한다.

6. 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.

7. 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.

8. 멤버십 할인 적용 여부를 입력 받는다.

9. 멤버십 할인을 적용할 경우, 프로모션 할인을 미적용한 금액의 30%를 계산한다.

10. 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용한다.

11. 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.

12. 추가 구매 여부를 입력 받는다. 

