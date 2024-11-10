package store.domain.promotion;

import store.validator.Validator;

public class BuyCount {
    private final int count;

    private BuyCount(int count) {
        this.count = count;
    }

    public static BuyCount of(String input) {
        Validator.validateNumber(input);
        return new BuyCount(Integer.parseInt(input));
    }

    public int getCount() {
        return count;
    }
}
