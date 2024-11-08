package store.domain.product;

public class Quantity {
    private final int quantity;

    private Quantity(int quantity) {
        this.quantity = quantity;
    }

    public static Quantity of(final String input) {
        return new Quantity(Integer.parseInt(input));
    }
}
