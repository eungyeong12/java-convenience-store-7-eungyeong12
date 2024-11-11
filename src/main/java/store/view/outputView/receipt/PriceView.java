package store.view.outputView.receipt;

import static store.controller.ConvenienceStoreController.products;
import static store.controller.ConvenienceStoreController.promotions;
import static store.controller.ConvenienceStoreController.purchasedProducts;

import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.PromotionProduct;
import store.domain.product.PromotionProducts;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;
import store.domain.user.PurchasedProducts;
import store.domain.user.Receipt;

public class PriceView {
    private static final String TOTAL = "총구매액";
    private static final String PROMOTION = "행사할인";
    private static final String MEMBERSHIP = "멤버십할인";
    private static final String RESULT = "내실돈";
    private static final String TOTAL_PRICE = "%-14s %,-9d %,d";
    private static final String PROMOTION_PRICE = "%-23s -%,d";
    private static final String MEMBERSHIP_PRICE = "%-30s -%,d";
    private static final String RESULT_PRICE = "%-23s %,d";
    private static final int DISCOUNT_RATE = 30;
    private static final int MAX_MEMBERSHIP_DISCOUNT_PRICE = 8000;

    public void displayPrice(boolean isMembershipDiscount, Receipt receipt) {
        int totalPrice = receipt.getTotalPrice();
        int notPromotionPrice = getNotPromotionPrice(receipt.getPurchasedProducts());
        int promotionDiscount = receipt.getTotalFreeGiftPrice();
        int membershipDiscount = getMembershipDiscount(isMembershipDiscount, notPromotionPrice);
        int result = getResultPrice(totalPrice, promotionDiscount, membershipDiscount);
        System.out.printf(TOTAL_PRICE + System.lineSeparator(), TOTAL, receipt.getTotalQuantity(), totalPrice);
        System.out.printf(PROMOTION_PRICE + System.lineSeparator(), PROMOTION, promotionDiscount);
        System.out.printf(MEMBERSHIP_PRICE + System.lineSeparator(), MEMBERSHIP, membershipDiscount);
        System.out.printf(RESULT_PRICE + System.lineSeparator(), RESULT, result);
    }

    private int getNotPromotionPrice(PurchasedProducts purchasedProducts) {
        int notPromotionPrice = 0;
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            notPromotionPrice += calculateNotPromotionPrice(productName, quantity, notPromotionPrice);
        }
        return notPromotionPrice;
    }

    private int calculateNotPromotionPrice(ProductName productName, Quantity quantity, int notPromotionPrice) {
        PromotionProducts promotionProducts = products.getPromotionProducts();
        if (promotionProducts.isExist(productName)) {
            notPromotionPrice += calculatePromotionProduct(productName);
        }
        if (!promotionProducts.isExist(productName)) {
            notPromotionPrice += quantity.getQuantity() * products.getProductPrice(productName);
        }
        return notPromotionPrice;
    }

    private int calculatePromotionProduct(ProductName productName) {
        PromotionProduct product = products.getPromotionProducts().getProduct(productName);
        Promotion promotion = promotions.getPromotion(product.getPromotion());
        int notPromotionQuantity = promotion.getPromotionNotPossible(
                purchasedProducts.getProductQuantity(productName), product);
        return notPromotionQuantity * products.getProductPrice(productName);
    }

    private int getMembershipDiscount(boolean isMembershipDiscount, int notPromotionPrice) {
        if (!isMembershipDiscount) {
            return 0;
        }
        int price = notPromotionPrice * DISCOUNT_RATE / 100;
        if (price > MAX_MEMBERSHIP_DISCOUNT_PRICE) {
            price = MAX_MEMBERSHIP_DISCOUNT_PRICE;
        }
        return price;
    }

    private int getResultPrice(int totalPrice, int promotionDiscount, int membershipDiscount) {
        return totalPrice - promotionDiscount - membershipDiscount;
    }
}
