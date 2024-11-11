package store.domain.promotion;

import java.time.LocalDateTime;
import store.util.DateFormatter;
import store.validator.Validator;

public class EndDate {
    private final LocalDateTime date;

    private EndDate(LocalDateTime date) {
        this.date = date;
    }

    public static EndDate of(String input) {
        Validator.validateBlankValue(input);
        return new EndDate(DateFormatter.parse(input));
    }

    public boolean isBefore(LocalDateTime now) {
        return date.isBefore(now);
    }
}
