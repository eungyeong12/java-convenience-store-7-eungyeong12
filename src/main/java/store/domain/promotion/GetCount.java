package store.domain.promotion;

import store.validator.Validator;

public class GetCount {
    private final int count;

    private GetCount(int count) {
        this.count = count;
    }

    public static GetCount of(String input) {
        Validator.validateNumber(input);
        return new GetCount(Integer.parseInt(input));
    }

    public int getCount() {
        return count;
    }
}
