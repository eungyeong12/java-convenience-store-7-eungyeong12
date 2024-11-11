package store.view.outputView.receipt;

import static store.controller.ConvenienceStoreController.products;

import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Quantity;
import store.domain.user.Receipt;

public class PurchaseProductsView {
    private static final String PRODUCT_CATEGORY = String.format("%-14s %-8s %s", "상품명", "수량", "금액");
    private static final String PRODUCT = "%-14s %,-9d %,d";

    public void displayPurchasedProducts(Receipt receipt) {
        StringBuilder result = new StringBuilder(PRODUCT_CATEGORY + System.lineSeparator());
        for (Entry<ProductName, Quantity> entry : receipt.getPurchasedProducts().getProducts().entrySet()) {
            ProductName name = entry.getKey();
            Quantity quantity = entry.getValue();
            int price = products.getProductPrice(name) * quantity.getQuantity();
            result.append(
                    String.format(PRODUCT + System.lineSeparator(), name.getName(), quantity.getQuantity(), price));
        }
        System.out.print(result);
    }
}
