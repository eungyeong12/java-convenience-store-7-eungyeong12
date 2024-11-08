package store.validator;

import store.exception.validation.ValidationErrorMessage;
import store.exception.validation.ValidationException;

public class Validator {
    public static final String NUMERIC_PATTERN = "-?\\d+";

    public static void validateBlankInput(String input) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(ValidationErrorMessage.BLANK_INPUT);
        }
    }

    public static int validateNumber(String input) {
        validateBlankValue(input);
        validateNumeric(input);
        return validateParseInt(input);
    }

    public static void validateBlankValue(String input) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(ValidationErrorMessage.BLANK_VALUE);
        }
    }

    private static void validateNumeric(String input) {
        if (!input.matches(NUMERIC_PATTERN)) {
            throw new ValidationException(ValidationErrorMessage.NOT_NUMERIC_VALUE, input);
        }
    }

    private static int validateParseInt(String input) {
        try {
            int number = Integer.parseInt(input);
            validateNegativeNumber(number);
            return number;
        } catch (NumberFormatException e) {
            throw new ValidationException(ValidationErrorMessage.OUT_OF_NUMBER_RANGE, input);
        }
    }

    private static void validateNegativeNumber(int number) {
        if (number < 0) {
            throw new ValidationException(ValidationErrorMessage.NEGATIVE_NUMBER_VALUE, String.valueOf(number));
        }
    }
}
