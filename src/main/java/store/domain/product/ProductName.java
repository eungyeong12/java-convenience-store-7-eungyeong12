package store.domain.product;

public class ProductName {
    private final String name;

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName of(final String input) {
        return new ProductName(input);
    }
}
