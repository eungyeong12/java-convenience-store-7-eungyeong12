package store.domain.product;

import java.util.ArrayList;
import java.util.List;
import store.exception.ConvenienceStoreException;

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
        throw new ConvenienceStoreException("상품없음");
    }
}
