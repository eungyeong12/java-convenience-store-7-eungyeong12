package store.service;

import static store.controller.CalculationController.freeGiftProducts;
import static store.controller.ConvenienceStoreController.products;
import static store.controller.ConvenienceStoreController.promotions;
import static store.controller.ConvenienceStoreController.purchasedProducts;

import store.constant.InputType;
import store.domain.product.GeneralProduct;
import store.domain.product.ProductName;
import store.domain.product.PromotionProduct;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;

public class CalculatorService {

    public int processBenefitAvailableProduct(String input, int stock, ProductName productName) {
        if (input.equalsIgnoreCase(InputType.Y.name())) {
            purchasedProducts.increaseQuantity(productName);
            stock += 1;
            freeGiftProducts.increaseQuantity(productName, new Quantity(1));
        }
        return stock;
    }

    public int processPromotionNotPossibleProduct(String input, int stock, int promotionNotPossible,
                                                  ProductName productName) {
        if (input.equalsIgnoreCase(InputType.Y.name())) {
            stock = decreaseGeneralProductStock(stock, promotionNotPossible, productName);
        }
        if (input.equalsIgnoreCase(InputType.N.name())) {
            stock -= promotionNotPossible;
            purchasedProducts.decreaseQuantity(productName, promotionNotPossible);
        }
        return stock;
    }

    public void calculatePromotionQuantity(Quantity quantity, ProductName productName, int stock) {
        PromotionProduct promotionProduct = products.getPromotionProducts().getProduct(productName);
        Promotion promotion = promotions.getPromotion(promotionProduct.getPromotion());
        int promotionQuantity = promotion.getPromotionQuantity(quantity.getQuantity(),
                promotionProduct.getQuantity());
        freeGiftProducts.increaseQuantity(productName, Quantity.of(String.valueOf(promotionQuantity)));
        promotionProduct.decreaseStock(stock);
    }

    private int decreaseGeneralProductStock(int stock, int promotionNotPossible, ProductName productName) {
        stock -= promotionNotPossible;
        GeneralProduct generalProduct = products.getGeneralProducts().getProduct(productName);
        if (generalProduct != null) {
            generalProduct.decreaseStock(promotionNotPossible);
        }
        return stock;
    }
}
