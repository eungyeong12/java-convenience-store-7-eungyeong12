package store.view.outputView;

import store.domain.product.GeneralProduct;
import store.domain.product.Product;
import store.domain.product.Products;
import store.domain.product.PromotionProduct;

public class ProductView {
    private static final String DASH = "- ";
    private static final String PRICE_RESULT = "%,d원 ";
    private static final String OUT_OF_STOCK = "재고 없음 ";
    private static final String QUANTITY_RESULT = "%,d개 ";

    public void displayProducts(Products products) {
        System.out.println(getProducts(products));
    }

    private String getProducts(Products products) {
        StringBuilder result = new StringBuilder();
        for (Product product : products.getProducts()) {
            result.append(getProduct(products, product));
        }
        return result.toString();
    }

    private String getProduct(Products products, Product product) {
        StringBuilder result = new StringBuilder(System.lineSeparator() + DASH);
        if (product instanceof PromotionProduct) {
            result.append(getPromotionProduct(products, product));
        }
        if (product instanceof GeneralProduct) {
            result.append(getGeneralProduct(product));
        }
        return result.toString();
    }

    private String getGeneralProduct(Product product) {
        return getProductName(product) + getPrice(product) + getQuantity(product);
    }

    private String getPromotionProduct(Products products, Product product) {
        String promotionProduct = getProductName(product) + getPrice(product)
                + getQuantity(product) + getPromotionName((PromotionProduct) product);
        StringBuilder result = new StringBuilder(promotionProduct);
        if (isNotExistGeneralProduct(products, product)) {
            result.append(System.lineSeparator()).append(getOutOfStockGeneralProduct(product));
        }
        return result.toString();
    }

    private boolean isNotExistGeneralProduct(Products products, Product product) {
        return !products.getGeneralProducts().isExist(product.getName());
    }

    private String getOutOfStockGeneralProduct(Product product) {
        return DASH + getProductName(product) + getPrice(product) + OUT_OF_STOCK;
    }

    private String getProductName(Product product) {
        return product.getName() + " ";
    }

    private String getPrice(Product product) {
        return String.format(PRICE_RESULT, product.getPrice());
    }

    private String getQuantity(Product product) {
        int quantity = product.getQuantity();
        if (quantity < 0) {
            return OUT_OF_STOCK;
        }
        return String.format(QUANTITY_RESULT, quantity);
    }

    private String getPromotionName(PromotionProduct product) {
        return product.getPromotion();
    }
}
