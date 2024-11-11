package store.domain.user;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.Quantity;
import store.exception.product.ProductErrorMessage;
import store.exception.product.ProductException;
import store.exception.validation.ValidationErrorMessage;
import store.exception.validation.ValidationException;
import store.util.Delimiter;

public class PurchasedProducts {
    private static final String PREFIX = "[";
    private static final String SUFFIX = "]";
    private static final int INPUT_DATA_SIZE = 2;

    private final Map<ProductName, Quantity> products;

    private PurchasedProducts(Map<ProductName, Quantity> products) {
        this.products = new LinkedHashMap<>(products);
    }

    public static PurchasedProducts of(String input, Products products) {
        List<String> tokens = Delimiter.splitWithComma(input);
        Map<ProductName, Quantity> purchasedProducts = new LinkedHashMap<>();
        for (String token : tokens) {
            addProduct(getData(token), purchasedProducts, products);
        }
        validateQuantity(products, purchasedProducts);
        return new PurchasedProducts(purchasedProducts);
    }

    private static List<String> getData(String token) {
        token = token.trim();
        validateInput(token);
        token = token.substring(1, token.length() - 1);
        List<String> data = Delimiter.splitWithDash(token);
        validateData(data);
        return data;
    }

    private static void addProduct(List<String> data, Map<ProductName, Quantity> purchasedProducts, Products products) {
        ProductName productName = ProductName.of(data.get(0).trim());
        validateExistProductName(products, productName);
        Quantity quantity = Quantity.of(data.get(1).trim());
        if (!purchasedProducts.containsKey(productName)) {
            purchasedProducts.put(productName, quantity);
            return;
        }
        addExistedProduct(purchasedProducts, productName, quantity);
    }

    private static void addExistedProduct(Map<ProductName, Quantity> purchasedProducts, ProductName productName,
                                          Quantity quantity) {
        int existingQuantity = purchasedProducts.get(productName).getQuantity();
        int newQuantity = existingQuantity + quantity.getQuantity();
        purchasedProducts.put(productName, new Quantity(newQuantity));
    }

    private static void validateInput(String token) {
        if (!token.startsWith(PREFIX) || !token.endsWith(SUFFIX)) {
            throw new ValidationException(ValidationErrorMessage.INVALID_INPUT);
        }
    }

    private static void validateExistProductName(Products products, ProductName productName) {
        if (!products.isExist(productName)) {
            throw new ProductException(ProductErrorMessage.NOT_EXIST_PRODUCT_NAME);
        }
    }

    private static void validateQuantity(Products products, Map<ProductName, Quantity> purchasedProducts) {
        for (Entry<ProductName, Quantity> entry : purchasedProducts.entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            if (products.getProductQuantity(productName) < quantity.getQuantity()) {
                throw new ProductException(ProductErrorMessage.OUT_OF_QUANTITY);
            }
        }
    }

    private static void validateData(List<String> data) {
        if (data.size() < INPUT_DATA_SIZE) {
            throw new ValidationException(ValidationErrorMessage.INVALID_INPUT);
        }
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

    public int getTotalQuantity() {
        return products.values().stream()
                .mapToInt(Quantity::getQuantity)
                .sum();
    }

    public int getTotalPrice(Products productInventory) {
        int price = 0;
        for (Entry<ProductName, Quantity> entry : products.entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            price += productInventory.getProductPrice(name) * quantity;
        }
        return price;
    }

    public Map<ProductName, Quantity> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
