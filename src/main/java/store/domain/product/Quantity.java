package store.domain.product;

import store.validator.Validator;

public class Quantity {
    private final int quantity;

    private Quantity(int quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(String input) {
        Validator.validateNumber(input);
        return new Quantity(Integer.parseInt(input));
    }

    public int getQuantity() {
        return quantity;
    }
}
