package store.domain.promotion;

public class Buy {
    private final int count;

    private Buy(int count) {
        this.count = count;
    }

    public static Buy of(final String input) {
        return new Buy(Integer.parseInt(input));
    }
}
