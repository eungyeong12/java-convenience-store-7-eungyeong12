package store.exception.validation;

public enum ValidationErrorMessage {
    BLANK_INPUT("입력값이 비어 있습니다."),
    BLANK_VALUE("비어 있는 값이 있습니다."),
    NOT_NUMERIC_VALUE("입력값은 숫자여야 합니다."),
    NEGATIVE_NUMBER_VALUE("입력값은 음수일 수 없습니다."),
    OUT_OF_NUMBER_RANGE("입력값이 너무 크거나 작습니다."),
    INVALID_DATE_FORMAT("날짜 형식이 올바르지 않습니다.");

    private final String message;

    ValidationErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
