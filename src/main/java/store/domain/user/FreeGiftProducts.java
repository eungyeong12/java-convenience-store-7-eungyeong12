package store.domain.user;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.Quantity;

public class FreeGiftProducts {
    private final Map<ProductName, Quantity> products;

    public FreeGiftProducts() {
        this.products = new LinkedHashMap<>();
    }

    public void increaseQuantity(ProductName productName, Quantity quantity) {
        this.products.put(productName, new Quantity(
                products.getOrDefault(productName, new Quantity(0)).getQuantity() + quantity.getQuantity()));
    }

    public int getTotalFreeGiftPrice(Products productInventory) {
        int price = 0;
        for (Entry<ProductName, Quantity> entry : products.entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            price += quantity * productInventory.getProductPrice(name);
        }
        return price;
    }

    public Map<ProductName, Quantity> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
