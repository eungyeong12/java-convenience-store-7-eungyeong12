package store.domain.promotion;

public class PromotionName {
    private final String name;

    private PromotionName(final String name) {
        this.name = name;
    }

    public static PromotionName of(final String input) {
        return new PromotionName(input);
    }
}
