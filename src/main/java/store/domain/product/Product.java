package store.domain.product;

import java.util.List;
import store.domain.promotion.PromotionName;
import store.util.Delimiter;

public class Product {
    private final ProductName name;
    private final Price price;
    private final Quantity quantity;
    private final PromotionName promotion;

    private Product(ProductName name, Price price, Quantity quantity, PromotionName promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product of(final String content) {
        List<String> tokens = Delimiter.splitWithDelimiter(content);
        ProductName name = ProductName.of(tokens.get(0));
        Price price = Price.of(tokens.get(1));
        Quantity quantity = Quantity.of(tokens.get(2));
        PromotionName promotion = PromotionName.of(tokens.get(3));
        return new Product(name, price, quantity, promotion);
    }
}
