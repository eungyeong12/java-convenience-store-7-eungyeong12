package store.domain.product;

import java.util.ArrayList;
import java.util.List;

public class PromotionProducts {
    private final List<PromotionProduct> products;

    public PromotionProducts(List<PromotionProduct> products) {
        this.products = new ArrayList<>(products);
    }
}
