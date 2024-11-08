package store.domain.promotion;

import java.util.Date;
import store.util.DateFormatter;

public class StartDate {
    private final Date date;

    private StartDate(final Date date) {
        this.date = date;
    }

    public static StartDate of(final String input) {
        return new StartDate(DateFormatter.parse(input));
    }
}
