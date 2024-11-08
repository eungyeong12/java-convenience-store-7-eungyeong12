package store.exception.validation;

import store.exception.ConvenienceStoreException;

public class ValidationException extends ConvenienceStoreException {
    private static final String invalidValue = " (잘못된 입력값: %s)";

    public ValidationException(ValidationErrorMessage message) {
        super(message.getMessage());
    }

    public ValidationException(ValidationErrorMessage message, String input) {
        super(message.getMessage() + String.format(invalidValue, input));
    }
}
