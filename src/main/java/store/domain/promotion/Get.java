package store.domain.promotion;

import store.validator.Validator;

public class Get {
    private final int count;

    private Get(int count) {
        this.count = count;
    }

    public static Get of(String input) {
        Validator.validateNumber(input);
        return new Get(Integer.parseInt(input));
    }
}
