package store.domain.product;

import store.validator.Validator;

public class Price {
    private final int price;

    private Price(int price) {
        this.price = price;
    }

    public static Price of(String input) {
        Validator.validateNumber(input);
        return new Price(Integer.parseInt(input));
    }

    public int getPrice() {
        return price;
    }
}
