package store.view.outputView;

import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.Quantity;
import store.domain.user.PurchasedProducts;

public class ReceiptView {
    private static final String TITLE = "==============W 편의점================";
    private static final String PRODUCT_CATEGORY = String.format("%-14s %-8s %s", "상품명", "수량", "금액");
    private static final String PRODUCT = "%-14s %-9s %s";
    private static final String FREE_GIFT_DIVIDER = "=============증\t정===============";
    private static final String AMOUNT_DIVIDER = "====================================";

    public void displayReceipt(Products products, PurchasedProducts purchasedProducts, boolean isMembershipDiscount) {
        System.out.println(System.lineSeparator() + TITLE);
        displayPurchasedProducts(products, purchasedProducts);
    }

    private void displayPurchasedProducts(Products products, PurchasedProducts purchasedProducts) {
        StringBuilder result = new StringBuilder(PRODUCT_CATEGORY + System.lineSeparator());
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            int price = products.getProductPrice(name) * quantity;
            result.append(String.format(PRODUCT + System.lineSeparator(), name.getName(), quantity, price));
        }
        System.out.println(result);
    }
}
