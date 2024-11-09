package store.domain.product;

import store.domain.promotion.PromotionName;

public class PromotionProduct extends Product {
    private final PromotionName promotion;

    public PromotionProduct(ProductName name, Price price, Quantity quantity, PromotionName promotion) {
        super(name, price, quantity);
        this.promotion = promotion;
    }

    public String getPromotion() {
        return promotion.getName();
    }
}
