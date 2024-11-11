package store.domain.promotion;

import java.time.LocalDateTime;
import store.util.DateFormatter;
import store.validator.Validator;

public class StartDate {
    private final LocalDateTime date;

    private StartDate(LocalDateTime date) {
        this.date = date;
    }

    public static StartDate of(String input) {
        Validator.validateBlankValue(input);
        return new StartDate(DateFormatter.parse(input));
    }

    public boolean isAfter(LocalDateTime now) {
        return date.isAfter(now);
    }
}
