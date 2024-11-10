package store.domain.promotion;

import java.util.Date;
import store.util.DateFormatter;
import store.validator.Validator;

public class StartDate {
    private final Date date;

    private StartDate(Date date) {
        this.date = date;
    }

    public static StartDate of(String input) {
        Validator.validateBlankValue(input);
        return new StartDate(DateFormatter.parse(input));
    }

    public Date getDate() {
        return date;
    }
}
