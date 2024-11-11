package store.exception.promotion;

public enum PromotionErrorMessage {
    NOT_EXIST_PROMOTION("프로모션이 존재하지 않습니다.");

    private final String message;

    PromotionErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
