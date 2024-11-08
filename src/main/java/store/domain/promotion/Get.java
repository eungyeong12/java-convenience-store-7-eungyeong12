package store.domain.promotion;

public class Get {
    private final int count;

    private Get(int count) {
        this.count = count;
    }

    public static Get of(final String input) {
        return new Get(Integer.parseInt(input));
    }
}
