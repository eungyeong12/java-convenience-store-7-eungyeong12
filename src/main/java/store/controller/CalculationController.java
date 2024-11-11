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

        boolean isMembershipDiscount = isMembershipDiscount();
        Receipt receipt = new Receipt(purchasedProducts, freeGiftProducts);
        outputView.displayReceipt(isMembershipDiscount, receipt);
    }

    private void calculateProduct(Entry<ProductName, Quantity> entry) {
        ProductName productName = entry.getKey();
        Quantity quantity = entry.getValue();
        int stock = quantity.getQuantity();
        PromotionProducts promotionProducts = products.getPromotionProducts();
        if (promotionProducts.isExist(productName)) {
            calculatePromotionProduct(productName, quantity, stock);
        }
        if (!promotionProducts.isExist(productName)) {
            calculateGeneralProduct(productName, quantity);
        }
    }

    private int getPromotionNotPossible(ProductName productName) {
        Promotion promotion = promotions.getPromotion(productName.getName());
        Quantity purchaseQuantity = purchasedProducts.getProductQuantity(productName);
        PromotionProduct promotionProduct = products.getPromotionProducts().getProduct(productName);
        return promotion.getPromotionNotPossible(purchaseQuantity, promotionProduct);
    }


    private void calculatePromotionProduct(ProductName productName, Quantity quantity, int stock) {
        PromotionProduct promotionProduct = products.getPromotionProducts().getProduct(productName);
        Promotion promotion = promotions.getPromotion(promotionProduct.getPromotion());
        if (promotion.isInvalidPromotion()) {
            return;
        }
        checkPromotionConditions(quantity, productName, stock);
    }

    private void checkPromotionConditions(Quantity quantity, ProductName productName, int stock) {
        stock += checkIsBenefitAvailable(productName, stock);

        stock += checkHasPromotionNotPossible(productName, stock);

        calculatorService.calculatePromotionQuantity(quantity, productName, stock);
    }

    private int checkIsBenefitAvailable(ProductName productName, int stock) {
        Promotion promotion = promotions.getPromotion(productName.getName());
        PromotionProduct promotionProduct = products.getPromotionProducts().getProduct(productName);
        Quantity purchaseQuantity = purchasedProducts.getProductQuantity(productName);
        if (promotion.isBenefitAvailable(promotionProduct.getQuantity(), purchaseQuantity)) {
            String input = getBenefitDecision(promotionProduct);
            stock += calculatorService.processBenefitAvailableProduct(input, stock, productName);
        }
        return stock;
    }

    private int checkHasPromotionNotPossible(ProductName productName, int stock) {
        PromotionProduct promotionProduct = products.getPromotionProducts().getProduct(productName);
        int promotionNotPossible = getPromotionNotPossible(productName);
        if (promotionNotPossible > 0) {
            String input = getDiscountDecision(promotionProduct, promotionNotPossible);
            stock += calculatorService.processPromotionNotPossibleProduct(input, stock, promotionNotPossible,
                    productName);
        }
        return stock;
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
