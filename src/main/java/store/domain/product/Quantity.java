package store.domain.product;

import store.validator.Validator;

public class Quantity {
    private int quantity;

    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(String input) {
        Validator.validateNumber(input);
        return new Quantity(Integer.parseInt(input));
    }

    public void decreaseStock(int buyQuantity) {
        quantity -= buyQuantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
