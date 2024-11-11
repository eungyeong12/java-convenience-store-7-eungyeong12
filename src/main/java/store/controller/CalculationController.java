package store.controller;

import static store.controller.ConvenienceStoreController.executeWithRetry;
import static store.controller.ConvenienceStoreController.products;
import static store.controller.ConvenienceStoreController.promotions;
import static store.controller.ConvenienceStoreController.purchasedProducts;

import java.util.Map.Entry;
import store.domain.product.GeneralProduct;
import store.domain.product.ProductName;
import store.domain.product.PromotionProduct;
import store.domain.product.PromotionProducts;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;
import store.domain.user.FreeGiftProducts;
import store.domain.user.Receipt;
import store.service.CalculatorService;
import store.view.inputView.InputView;
import store.view.outputView.OutputView;

public class CalculationController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CalculatorService calculatorService;

    public static FreeGiftProducts freeGiftProducts;

    public CalculationController(InputView inputView, OutputView outputView, CalculatorService calculatorService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.calculatorService = calculatorService;
    }

    public void calculate() {
        freeGiftProducts = new FreeGiftProducts();

        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            calculateProduct(entry);
        }

        Receipt receipt = new Receipt(purchasedProducts, freeGiftProducts);
        boolean isMembershipDiscount = isMembershipDiscount();
        outputView.displayReceipt(products, promotions, isMembershipDiscount, receipt);
    }

    private void calculateProduct(Entry<ProductName, Quantity> entry) {
        ProductName productName = entry.getKey();
        Quantity quantity = entry.getValue();
        int stock = quantity.getQuantity();
        PromotionProducts promotionProducts = products.getPromotionProducts();
        if (promotionProducts.isExist(productName)) {
            calculatePromotionProduct(promotionProducts, productName, quantity, stock);
        }
        if (!promotionProducts.isExist(productName)) {
            calculateGeneralProduct(productName, quantity);
        }
    }

    private int getPromotionNotPossible(Promotion promotion, Quantity productQuantity,
                                        PromotionProduct promotionProduct) {
        return promotion.getPromotionNotPossible(productQuantity, promotionProduct);
    }


    private void calculatePromotionProduct(PromotionProducts promotionProducts, ProductName productName,
                                           Quantity quantity, int stock) {
        PromotionProduct promotionProduct = promotionProducts.getProduct(productName);
        Promotion promotion = promotions.getPromotion(promotionProduct.getPromotion());
        if (promotion.isInvalidPromotion()) {
            return;
        }
        checkPromotionConditions(quantity, promotion, promotionProduct, productName, stock);
    }

    private void checkPromotionConditions(Quantity quantity, Promotion promotion,
                                          PromotionProduct promotionProduct, ProductName productName, int stock) {
        checkIsBenefitAvailable(promotion, promotionProduct, productName, stock);

        checkHasPromotionNotPossible(promotion, promotionProduct, productName, stock);

        calculatorService.calculatePromotionQuantity(promotion, quantity, promotionProduct, productName, stock);
    }

    private void checkIsBenefitAvailable(Promotion promotion, PromotionProduct promotionProduct,
                                         ProductName productName, int stock) {
        if (promotion.isBenefitAvailable(promotionProduct.getQuantity(),
                purchasedProducts.getProductQuantity(productName))) {
            String input = getBenefitDecision(promotionProduct);
            calculatorService.processBenefitAvailableProduct(input, stock, productName);
        }
    }

    private void checkHasPromotionNotPossible(Promotion promotion, PromotionProduct promotionProduct,
                                              ProductName productName, int stock) {
        int promotionNotPossible = getPromotionNotPossible(promotion, purchasedProducts.getProductQuantity(productName),
                promotionProduct);
        if (promotionNotPossible > 0) {
            String input = getDiscountDecision(promotionProduct, promotionNotPossible);
            calculatorService.processPromotionNotPossibleProduct(input, stock, promotionNotPossible, productName);
        }
    }

    private void calculateGeneralProduct(ProductName productName, Quantity quantity) {
        GeneralProduct product = products.getGeneralProducts().getProduct(productName);
        product.decreaseStock(quantity.getQuantity());
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
}
