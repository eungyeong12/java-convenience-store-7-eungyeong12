package store.validator;

import store.exception.validation.ValidationErrorMessage;
import store.exception.validation.ValidationException;

public class Validator {
    private static final String NUMERIC_PATTERN = "-?\\d+";

    public static void validateBlankInput(String input) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(ValidationErrorMessage.BLANK_INPUT);
        }
    }

    public static void validateNumber(String input) {
        validateBlankValue(input);
        validateNumeric(input);
        validateValidNumber(input);
    }

    public static void validateBlankValue(String input) {
        if (input == null || input.isBlank()) {
            throw new ValidationException(ValidationErrorMessage.BLANK_VALUE);
        }
    }

    public static void validateInputYOrN(String input) {
        if (!(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N"))) {
            throw new ValidationException(ValidationErrorMessage.NOT_Y_OR_N);
        }
    }

    private static void validateNumeric(String input) {
        if (!input.matches(NUMERIC_PATTERN)) {
            throw new ValidationException(ValidationErrorMessage.NOT_NUMERIC_VALUE, input);
        }
    }

    private static void validateValidNumber(String input) {
        try {
            int number = Integer.parseInt(input);
            validateNegativeNumber(number);
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
