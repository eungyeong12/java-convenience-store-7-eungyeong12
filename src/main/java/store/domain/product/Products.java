package store.domain.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import store.domain.promotion.PromotionName;
import store.util.Delimiter;

public class Products {
    private static final String NO_PROMOTION = "null";

    private final List<Product> products;

    private Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public static Products of(List<String> contents) {
        List<Product> products = IntStream.range(1, contents.size())
                .mapToObj(i -> Delimiter.splitWithDelimiter(contents.get(i)))
                .map(Products::convertToProduct)
                .toList();
        return new Products(products);
    }

    public GeneralProducts getGeneralProducts() {
        List<GeneralProduct> generalProducts = products.stream().
                filter(product -> product instanceof GeneralProduct)
                .map(product -> (GeneralProduct) product)
                .toList();
        return new GeneralProducts(generalProducts);
    }

    public PromotionProducts getPromotionProducts() {
        List<PromotionProduct> promotionProducts = products.stream()
                .filter(product -> product instanceof PromotionProduct)
                .map(product -> (PromotionProduct) product)
                .toList();
        return new PromotionProducts(promotionProducts);
    }

    private static Product convertToProduct(List<String> tokens) {
        ProductName name = ProductName.of(tokens.get(0));
        Price price = Price.of(tokens.get(1));
        Quantity quantity = Quantity.of(tokens.get(2));
        PromotionName promotion = PromotionName.of(tokens.get(3));
        if (isGeneralProduct(promotion)) {
            return new GeneralProduct(name, price, quantity);
        }
        return new PromotionProduct(name, price, quantity, promotion);
    }

    private static boolean isGeneralProduct(PromotionName promotion) {
        return promotion.getName().equals(NO_PROMOTION);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
}
