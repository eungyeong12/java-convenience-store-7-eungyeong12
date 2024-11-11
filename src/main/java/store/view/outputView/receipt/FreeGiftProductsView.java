package store.view.outputView.receipt;

import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Quantity;
import store.domain.user.Receipt;

public class FreeGiftProductsView {
    private static final String FREE_GIFT = "%-14s %,-9d";

    public void displayFreeGifts(Receipt receipt) {
        StringBuilder result = new StringBuilder();
        for (Entry<ProductName, Quantity> entry : receipt.getFreeGiftProducts().getProducts().entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            result.append(String.format(FREE_GIFT + System.lineSeparator(), name.getName(), quantity));
        }
        System.out.print(result);
    }
}
