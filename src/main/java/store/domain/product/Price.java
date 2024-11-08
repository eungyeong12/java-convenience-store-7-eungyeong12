package store.domain.product;

public class Price {
    private final int price;

    private Price(int price) {
        this.price = price;
    }

    public static Price of(final String input) {
        return new Price(Integer.parseInt(input));
    }
}
