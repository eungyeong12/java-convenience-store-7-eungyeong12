package store.domain.product;

import store.validator.Validator;

public class ProductName {
    private final String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName of(String input) {
        Validator.validateBlankValue(input);
        return new ProductName(input);
    }

    public String getName() {
        return name;
    }
}
