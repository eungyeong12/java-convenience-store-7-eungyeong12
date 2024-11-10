package store.controller;

import java.util.Map.Entry;
import java.util.function.Supplier;
import store.constant.FileName;
import store.domain.product.GeneralProduct;
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
            int stock = quantity.getQuantity();
            if (promotionProducts.isExist(productName)) {
                PromotionProduct product = promotionProducts.getProduct(productName);
                Promotion promotion = promotions.getPromotion(product.getPromotion());
                if (promotion.isBenefitAvailable(product.getQuantity(),
                        purchasedProducts.getProductQuantity(productName))) {
                    String s = getBenefitDecision(product);
                    if (s.equalsIgnoreCase("Y")) {
                        purchasedProducts.increaseQuantity(productName);
                        stock += 1;
                    }
                }

                int discountNotPossible = promotion.getDiscountNotPossible(
                        purchasedProducts.getProductQuantity(productName));
                if (discountNotPossible > 0) {
                    String s = getDiscountDecision(product, discountNotPossible);
                    if (s.equalsIgnoreCase("Y")) {
                        stock -= discountNotPossible;
                        GeneralProduct generalProduct = products.getGeneralProducts().getProduct(productName);
                        generalProduct.decreaseStock(discountNotPossible);
                    }
                    if (s.equalsIgnoreCase("N")) {
                        stock -= discountNotPossible;
                        purchasedProducts.decreaseQuantity(productName, discountNotPossible);
                    }
                }
                product.decreaseStock(stock);
            }

            if (!promotionProducts.isExist(productName)) {
                GeneralProduct product = products.getGeneralProducts().getProduct(productName);
                product.decreaseStock(quantity.getQuantity());
            }
        }

        boolean isMembershipDiscount = isMembershipDiscount();

        outputView.displayReceipt(products, purchasedProducts, isMembershipDiscount);
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

    private String getBenefitDecision(PromotionProduct product) {
        return executeWithRetry(() -> inputView.getBenefitDecision(product));
    }

    private String getDiscountDecision(PromotionProduct product, int discountNotPossible) {
        return executeWithRetry(() -> inputView.getDiscountDecision(product, discountNotPossible));
    }

    private boolean isMembershipDiscount() {
        return executeWithRetry(inputView::isMembershipDiscount);
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


