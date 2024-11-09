package store.domain.promotion;

import store.validator.Validator;

public class Buy {
    private final int count;

    private Buy(int count) {
        this.count = count;
    }

    public static Buy of(String input) {
        Validator.validateNumber(input);
        return new Buy(Integer.parseInt(input));
    }
}
