package store.exception.product;

public enum ProductErrorMessage {
    NOT_EXIST_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OUT_OF_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    NOT_EXIST_PRODUCT("상품이 존재하지 않습니다.");

    private final String message;

    ProductErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
