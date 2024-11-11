package store.domain.product;

import java.util.ArrayList;
import java.util.List;
import store.exception.product.ProductErrorMessage;
import store.exception.product.ProductException;

public class PromotionProducts {
    private final List<PromotionProduct> products;

    public PromotionProducts(List<PromotionProduct> products) {
        this.products = new ArrayList<>(products);
    }

    public boolean isExist(ProductName productName) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(productName.getName()));
    }

    public PromotionProduct getProduct(ProductName productName) {
        for (PromotionProduct product : products) {
            if (product.getName().equals(productName.getName())) {
                return product;
            }
        }
        throw new ProductException(ProductErrorMessage.NOT_EXIST_PRODUCT, productName.getName());
    }
}
