package store.controller;

import java.util.Map.Entry;
import java.util.function.Supplier;
import store.constant.FileName;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.PromotionProduct;
import store.domain.product.PromotionProducts;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.domain.user.PurchasedProducts;
import store.exception.ConvenienceStoreException;
import store.util.FileReader;
import store.view.inputView.InputView;
import store.view.outputView.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;

    public ConvenienceStoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Products products = getProducts();
        Promotions promotions = getPromotions();
        outputView.displayProducts(products);

        PurchasedProducts purchasedProducts = getPurchasedProducts(products);

        PromotionProducts promotionProducts = products.getPromotionProducts();
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            if (promotionProducts.isExist(productName)) {
                PromotionProduct product = (PromotionProduct) products.getPromotionProducts().getProduct(productName);
                Promotion promotion = promotions.getPromotion(product.getPromotion());
            }
        }
    }

    private Products getProducts() {
        return Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()));
    }

    private Promotions getPromotions() {
        return Promotions.of(FileReader.getContents(FileName.PROMOTION_FILE.getName()));
    }

    private PurchasedProducts getPurchasedProducts(Products products) {
        return executeWithRetry(() -> PurchasedProducts.of(inputView.getProductAndQuantity(), products));
    }

    private <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (ConvenienceStoreException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


