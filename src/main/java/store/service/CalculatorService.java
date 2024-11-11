package store.service;

import static store.controller.CalculationController.freeGiftProducts;
import static store.controller.ConvenienceStoreController.products;
import static store.controller.ConvenienceStoreController.purchasedProducts;

import store.constant.InputType;
import store.domain.product.GeneralProduct;
import store.domain.product.ProductName;
import store.domain.product.PromotionProduct;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;

public class CalculatorService {

    public void processBenefitAvailableProduct(String input, int stock, ProductName productName) {
        if (input.equalsIgnoreCase(InputType.Y.name())) {
            purchasedProducts.increaseQuantity(productName);
            stock += 1;
            freeGiftProducts.increaseQuantity(productName, new Quantity(1));
        }
    }

    public void processPromotionNotPossibleProduct(String input, int stock, int promotionNotPossible,
                                                   ProductName productName) {
        if (input.equalsIgnoreCase(InputType.Y.name())) {
            stock -= promotionNotPossible;
            GeneralProduct generalProduct = products.getGeneralProducts().getProduct(productName);
            generalProduct.decreaseStock(promotionNotPossible);
        }
        if (input.equalsIgnoreCase(InputType.N.name())) {
            stock -= promotionNotPossible;
            purchasedProducts.decreaseQuantity(productName, promotionNotPossible);
        }
    }

    public void calculatePromotionQuantity(Promotion promotion, Quantity quantity, PromotionProduct promotionProduct,
                                           ProductName productName, int stock) {
        int promotionQuantity = promotion.getPromotionQuantity(quantity.getQuantity(),
                promotionProduct.getQuantity());
        freeGiftProducts.increaseQuantity(productName, Quantity.of(String.valueOf(promotionQuantity)));
        promotionProduct.decreaseStock(stock);
    }
}
