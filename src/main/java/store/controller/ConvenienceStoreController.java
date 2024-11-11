package store.controller;

import java.util.function.Supplier;
import store.config.ApplicationConfig;
import store.constant.FileName;
import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.domain.user.PurchasedProducts;
import store.exception.ConvenienceStoreException;
import store.util.FileReader;
import store.view.inputView.InputView;
import store.view.outputView.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;

    public static Products products;
    public static Promotions promotions;
    public static PurchasedProducts purchasedProducts;

    public ConvenienceStoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        init();
        do {
            start();
        } while (isAdditionalPurchase());
    }

    private void init() {
        products = readProducts();
        promotions = readPromotions();
    }

    private void start() {
        outputView.displayProducts(products);
        purchasedProducts = getPurchasedProducts();
        CalculationController calculationController = new ApplicationConfig().calculationController();
        calculationController.calculate();
    }

    private Products readProducts() {
        return Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()));
    }

    private Promotions readPromotions() {
        return Promotions.of(FileReader.getContents(FileName.PROMOTION_FILE.getName()));
    }

    private PurchasedProducts getPurchasedProducts() {
        return executeWithRetry(() -> PurchasedProducts.of(inputView.getProductAndQuantity(), products));
    }

    private boolean isAdditionalPurchase() {
        return executeWithRetry(inputView::isAdditionalPurchase);
    }

    public static <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (ConvenienceStoreException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


