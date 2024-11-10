package store.view.outputView;

import store.domain.product.Products;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. w편의점입니다.";
    private static final String PRODUCT_GUIDE_MESSAGE = "현재 보유하고 있는 상품입니다.";

    private final ProductView productView;

    public OutputView(ProductView productView) {
        this.productView = productView;
    }

    public void displayProducts(Products products) {
        System.out.println(System.lineSeparator() + WELCOME_MESSAGE + System.lineSeparator() + PRODUCT_GUIDE_MESSAGE);
        productView.displayProducts(products);
    }
}
