package store.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateFormatter {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String format(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static LocalDateTime parse(String input) {
        List<String> date = Delimiter.splitWithDash(input);
        int year = Integer.parseInt(date.get(0));
        int month = Integer.parseInt(date.get(1));
        int day = Integer.parseInt(date.get(2));
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }
}
