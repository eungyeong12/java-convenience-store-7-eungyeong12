package store.domain.promotion;

import java.util.Date;
import store.util.DateFormatter;

public class EndDate {
    private final Date date;

    private EndDate(final Date date) {
        this.date = date;
    }

    public static EndDate of(final String input) {
        return new EndDate(DateFormatter.parse(input));
    }
}
