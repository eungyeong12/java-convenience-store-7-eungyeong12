package store.domain.promotion;

import store.validator.Validator;

public class PromotionName {
    private final String name;

    private PromotionName(final String name) {
        this.name = name;
    }

    public static PromotionName of(String input) {
        Validator.validateBlankValue(input);
        return new PromotionName(input);
    }

    public String getName() {
        return name;
    }
}
