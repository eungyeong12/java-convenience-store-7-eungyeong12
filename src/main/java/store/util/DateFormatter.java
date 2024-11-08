package store.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import store.exception.validation.ValidationErrorMessage;
import store.exception.validation.ValidationException;

public class DateFormatter {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return formatter.format(date);
    }

    public static Date parse(String input) {
        try {
            return formatter.parse(input);
        } catch (ParseException e) {
            throw new ValidationException(ValidationErrorMessage.INVALID_DATE_FORMAT, input);
        }
    }
}
