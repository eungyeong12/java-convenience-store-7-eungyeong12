package store.domain.user;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.Quantity;
import store.exception.ConvenienceStoreException;
import store.util.Delimiter;

public class PurchasedProducts {
    private final Map<ProductName, Quantity> products;

    private PurchasedProducts(Map<ProductName, Quantity> products) {
        this.products = new LinkedHashMap<>(products);
    }

    public static PurchasedProducts of(String input, Products products) {
        List<String> tokens = Delimiter.splitWithComma(input);
        Map<ProductName, Quantity> purchasedProducts = new LinkedHashMap<>();
        for (String token : tokens) {
            token = token.trim();
            if (!token.startsWith("[") || !token.endsWith("]")) {
                throw new ConvenienceStoreException("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }
            token = token.substring(1, token.length() - 1);
            List<String> data = Delimiter.splitWithDash(token);
            ProductName productName = ProductName.of(data.get(0).trim());
            if (!products.isExist(productName)) {
                throw new ConvenienceStoreException("존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
            Quantity quantity = Quantity.of(data.get(1).trim());
            if (!purchasedProducts.containsKey(productName)) {
                purchasedProducts.put(productName, quantity);
                continue;
            }
            purchasedProducts.put(productName,
                    Quantity.of(
                            String.valueOf(
                                    purchasedProducts.get(productName).getQuantity() + quantity.getQuantity())));
        }

        for (Entry<ProductName, Quantity> entry : purchasedProducts.entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            if (products.getProductQuantity(productName) < quantity.getQuantity()) {
                throw new ConvenienceStoreException("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }

        return new PurchasedProducts(purchasedProducts);
    }

    public Map<ProductName, Quantity> getProducts() {
        return Collections.unmodifiableMap(products);
    }

    public void increaseQuantity(ProductName productName) {
        int existed = products.get(productName).getQuantity();
        Quantity newQuantity = Quantity.of(String.valueOf(existed + 1));
        products.replace(productName, newQuantity);
    }

    public void decreaseQuantity(ProductName productName, int discountNotPossible) {
        int existed = products.get(productName).getQuantity();
        Quantity newQuantity = Quantity.of(String.valueOf(existed - discountNotPossible));
        products.replace(productName, newQuantity);
    }

    public Quantity getProductQuantity(ProductName productName) {
        return products.get(productName);
    }
}
