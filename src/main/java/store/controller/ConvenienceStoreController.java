package store.controller;

import store.constant.FileName;
import store.domain.product.Products;
import store.util.FileReader;

public class ConvenienceStoreController {

    public void run() {
        init();
    }

    private void init() {
        setProducts();
    }

    private void setProducts() {
        Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()));
    }
}
