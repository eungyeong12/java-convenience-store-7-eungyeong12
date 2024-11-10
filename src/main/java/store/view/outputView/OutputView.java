package store.view.outputView;

import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.domain.user.PurchasedProducts;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. w편의점입니다.";
    private static final String PRODUCT_GUIDE_MESSAGE = "현재 보유하고 있는 상품입니다.";

    private final ProductView productView;
    private final ReceiptView receiptView;

    public OutputView(ProductView productView, ReceiptView receiptView) {
        this.productView = productView;
        this.receiptView = receiptView;
    }

    public void displayProducts(Products products) {
        System.out.println(System.lineSeparator() + WELCOME_MESSAGE + System.lineSeparator() + PRODUCT_GUIDE_MESSAGE);
        productView.displayProducts(products);
    }

    public void displayReceipt(Products products, Promotions promotions, PurchasedProducts purchasedProducts,
                               boolean isMembershipDiscount) {
        receiptView.displayReceipt(products, promotions, purchasedProducts, isMembershipDiscount);
    }
}
