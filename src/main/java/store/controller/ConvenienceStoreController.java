package store.controller;

import store.constant.FileName;
import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.util.FileReader;

public class ConvenienceStoreController {

    public void run() {
        init();
    }

    private void init() {
        setProducts();
        setPromotions();
    }

    private void setProducts() {
        Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()));
    }

    private void setPromotions() {
        Promotions.of(FileReader.getContents(FileName.PROMOTION_FILE.getName()));
    }
}
