package store.controller;

import store.constant.FileName;
import store.domain.product.Products;
import store.domain.promotion.Promotions;
import store.util.FileReader;
import store.view.outputView.OutputView;

public class ConvenienceStoreController {
    private final OutputView outputView;

    public ConvenienceStoreController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        Products products = getProducts();
        Promotions promotions = getPromotions();
        outputView.displayProducts(products);

    }

    private Products getProducts() {
        return Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()));
    }

    private Promotions getPromotions() {
        return Promotions.of(FileReader.getContents(FileName.PROMOTION_FILE.getName()));
    }
}
