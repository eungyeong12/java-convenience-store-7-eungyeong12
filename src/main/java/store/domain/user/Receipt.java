package store.domain.user;

import static store.controller.ConvenienceStoreController.products;

public class Receipt {
    private final PurchasedProducts purchasedProducts;
    private final FreeGiftProducts freeGiftProducts;

    public Receipt(PurchasedProducts purchasedProducts, FreeGiftProducts freeGiftProducts) {
        this.purchasedProducts = purchasedProducts;
        this.freeGiftProducts = freeGiftProducts;
    }

    public int getTotalPrice() {
        return purchasedProducts.getTotalPrice(products);
    }

    public int getTotalQuantity() {
        return purchasedProducts.getTotalQuantity();
    }

    public int getTotalFreeGiftPrice() {
        return freeGiftProducts.getTotalFreeGiftPrice(products);
    }

    public PurchasedProducts getPurchasedProducts() {
        return purchasedProducts;
    }

    public FreeGiftProducts getFreeGiftProducts() {
        return freeGiftProducts;
    }
}
