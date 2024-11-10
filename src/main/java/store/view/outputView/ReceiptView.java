package store.view.outputView;

import java.util.Map.Entry;
import store.domain.product.ProductName;
import store.domain.product.Products;
import store.domain.product.PromotionProduct;
import store.domain.product.PromotionProducts;
import store.domain.product.Quantity;
import store.domain.promotion.Promotion;
import store.domain.promotion.Promotions;
import store.domain.user.PurchasedProducts;

public class ReceiptView {
    private static final String TITLE = "==============W 편의점================";
    private static final String PRODUCT_CATEGORY = String.format("%-14s %-8s %s", "상품명", "수량", "금액");
    private static final String PRODUCT = "%-14s %,-9d %,d";
    private static final String FREE_GIFT_DIVIDER = "=============증\t정===============";
    private static final String FREE_GIFT = "%-14s %,-9d";
    private static final String PRICE_DIVIDER = "====================================";
    private static final String TOTAL = "총구매액";
    private static final String PROMOTION = "행사할인";
    private static final String MEMBERSHIP = "멤버십할인";
    private static final String RESULT = "내실돈";
    private static final String TOTAL_PRICE = "%-14s %,-9d %,d";
    private static final String PROMOTION_PRICE = "%-23s -%,d";
    private static final String MEMBERSHIP_PRICE = "%-30s -%,d";
    private static final String RESULT_PRICE = "%-23s %,d";

    public void displayReceipt(Products products, Promotions promotions, PurchasedProducts purchasedProducts,
                               boolean isMembershipDiscount) {
        System.out.println(System.lineSeparator() + TITLE);
        displayPurchasedProducts(products, purchasedProducts);
        System.out.println(FREE_GIFT_DIVIDER);
        displayFreeGifts(products, promotions, purchasedProducts);
        System.out.println(PRICE_DIVIDER);
        displayPrice(products, promotions, purchasedProducts, isMembershipDiscount);
    }

    private void displayPurchasedProducts(Products products, PurchasedProducts purchasedProducts) {
        StringBuilder result = new StringBuilder(PRODUCT_CATEGORY + System.lineSeparator());
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            int price = products.getProductPrice(name) * quantity;
            result.append(String.format(PRODUCT + System.lineSeparator(), name.getName(), quantity, price));
        }
        System.out.print(result);
    }

    private void displayFreeGifts(Products products, Promotions promotions, PurchasedProducts purchasedProducts) {
        StringBuilder result = new StringBuilder();
        PromotionProducts promotionProducts = products.getPromotionProducts();
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            if (!promotionProducts.isExist(name)) {
                continue;
            }
            if (promotions.getPromotion(products.getPromotionProducts().getProduct(name).getPromotion())
                    .isInvalidPromotion()) {
                continue;
            }
            PromotionProduct product = promotionProducts.getProduct(name);
            Promotion promotion = promotions.getPromotion(product.getPromotion());

            int promotionQuantity = promotion.getPromotionQuantity(quantity);
            if (quantity > products.getProductQuantity(name)) {
                promotionQuantity = promotion.getPromotionQuantity(products.getProductQuantity(name));
            }
            result.append(String.format(FREE_GIFT + System.lineSeparator(), name.getName(), promotionQuantity));
        }
        System.out.print(result);
    }

    private void displayPrice(Products products, Promotions promotions, PurchasedProducts purchasedProducts,
                              boolean isMembershipDiscount) {
        int totalPrice = getTotalPrice(products, purchasedProducts);
        int notPromotionPrice = getNotPromotionPrice(products, promotions, purchasedProducts);
        int promotionDiscount = getPromotionDiscount(products, promotions, purchasedProducts);
        int membershipDiscount = getMembershipDiscount(isMembershipDiscount, notPromotionPrice);
        System.out.println(String.format(TOTAL_PRICE, TOTAL, getTotalQuantity(purchasedProducts), totalPrice));
        System.out.println(
                String.format(PROMOTION_PRICE, PROMOTION, promotionDiscount));
        System.out.println(
                String.format(MEMBERSHIP_PRICE, MEMBERSHIP, membershipDiscount));
        System.out.println(String.format(RESULT_PRICE, RESULT,
                getResultPrice(totalPrice, promotionDiscount, membershipDiscount)));
    }

    private int getTotalQuantity(PurchasedProducts products) {
        return products.getProducts().values().stream()
                .mapToInt(Quantity::getQuantity)
                .sum();
    }

    private int getTotalPrice(Products products, PurchasedProducts purchasedProducts) {
        int price = 0;
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName name = entry.getKey();
            int quantity = entry.getValue().getQuantity();
            price += products.getProductPrice(name) * quantity;
        }
        return price;
    }

    private int getPromotionDiscount(Products products, Promotions promotions, PurchasedProducts purchasedProducts) {
        PromotionProducts promotionProducts = products.getPromotionProducts();
        int price = 0;
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName name = entry.getKey();
            if (!promotionProducts.isExist(name)) {
                continue;
            }
            if (promotions.getPromotion(products.getPromotionProducts().getProduct(name).getPromotion())
                    .isInvalidPromotion()) {
                continue;
            }
            int quantity = entry.getValue().getQuantity();
            PromotionProduct product = promotionProducts.getProduct(name);
            Promotion promotion = promotions.getPromotion(product.getPromotion());

            int promotionQuantity = promotion.getPromotionQuantity(quantity);
            if (quantity > products.getProductQuantity(name)) {
                promotionQuantity = promotion.getPromotionQuantity(products.getProductQuantity(name));
            }
            price += promotionQuantity * products.getProductPrice(name);
        }
        return price;
    }

    private int getNotPromotionPrice(Products products, Promotions promotions, PurchasedProducts purchasedProducts) {
        PromotionProducts promotionProducts = products.getPromotionProducts();
        int notPromotionPrice = 0;
        for (Entry<ProductName, Quantity> entry : purchasedProducts.getProducts().entrySet()) {
            ProductName productName = entry.getKey();
            Quantity quantity = entry.getValue();
            if (promotionProducts.isExist(productName)) {
                PromotionProduct product = promotionProducts.getProduct(productName);
                Promotion promotion = promotions.getPromotion(product.getPromotion());

                int notPromotionQuantity = promotion.getDiscountNotPossible(
                        purchasedProducts.getProductQuantity(productName));
                if (product.getQuantity() < purchasedProducts.getProductQuantity(productName).getQuantity()) {
                    notPromotionQuantity += purchasedProducts.getProductQuantity(productName).getQuantity()
                            - product.getQuantity();
                }
                notPromotionPrice += notPromotionQuantity * products.getProductPrice(productName);
            }

            if (!promotionProducts.isExist(productName)) {
                notPromotionPrice += quantity.getQuantity() * products.getProductPrice(productName);
            }
        }
        return notPromotionPrice;
    }

    private int getMembershipDiscount(boolean isMembershipDiscount, int notPromotionPrice) {
        if (!isMembershipDiscount) {
            return 0;
        }
        int price = notPromotionPrice * 30 / 100;
        if (price > 8000) {
            price = 8000;
        }
        return price;
    }

    private int getResultPrice(int totalPrice, int promotionDiscount, int membershipDiscount) {
        return totalPrice - promotionDiscount - membershipDiscount;
    }
}
