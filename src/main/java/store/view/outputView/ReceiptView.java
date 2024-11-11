package store.view.outputView;

import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.domain.user.Receipt;
import store.view.outputView.receipt.FreeGiftProductsView;
import store.view.outputView.receipt.PriceView;
import store.view.outputView.receipt.PurchaseProductsView;

public class ReceiptView {
    private static final String TITLE = "==============W 편의점================";
    private static final String FREE_GIFT_DIVIDER = "=============증\t정===============";
    private static final String PRICE_DIVIDER = "====================================";

    private final PurchaseProductsView purchasedProductsView;
    private final FreeGiftProductsView freeGiftProductsView;
    private final PriceView priceView;

    public ReceiptView(PurchaseProductsView purchasedProductsView, FreeGiftProductsView freeGiftProductsView,
                       PriceView priceView) {
        this.purchasedProductsView = purchasedProductsView;
        this.freeGiftProductsView = freeGiftProductsView;
        this.priceView = priceView;
    }

    public void displayReceipt(Products products, Promotions promotions,
                               boolean isMembershipDiscount, Receipt receipt) {
        System.out.println(System.lineSeparator() + TITLE);
        purchasedProductsView.displayPurchasedProducts(products, receipt);
        System.out.println(FREE_GIFT_DIVIDER);
        freeGiftProductsView.displayFreeGifts(receipt);
        System.out.println(PRICE_DIVIDER);
        priceView.displayPrice(products, promotions, isMembershipDiscount, receipt);
    }
}
