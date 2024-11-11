package store.domain.product;

import java.util.ArrayList;
import java.util.List;
import store.exception.product.ProductErrorMessage;
import store.exception.product.ProductException;

public class GeneralProducts {
    private final List<GeneralProduct> products;

    public GeneralProducts(List<GeneralProduct> products) {
        this.products = new ArrayList<>(products);
    }

    public boolean isExist(String name) {
        return products.stream()
                .anyMatch(product -> product.getName().equals(name));
    }

    public GeneralProduct getProduct(ProductName productName) {
        for (GeneralProduct product : products) {
            if (product.getName().equals(productName.getName())) {
                return product;
            }
        }
        throw new ProductException(ProductErrorMessage.NOT_EXIST_PRODUCT, productName.getName());
    }
}
