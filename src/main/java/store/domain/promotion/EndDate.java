package store.domain.promotion;

import java.util.Date;
import store.util.DateFormatter;
import store.validator.Validator;

public class EndDate {
    private final Date date;

    private EndDate(Date date) {
        this.date = date;
    }

    public static EndDate of(String input) {
        Validator.validateBlankValue(input);
        return new EndDate(DateFormatter.parse(input));
    }
}
