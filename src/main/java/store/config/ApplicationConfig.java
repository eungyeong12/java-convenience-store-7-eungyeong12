package store.config;

import store.controller.ConvenienceStoreController;
import store.view.outputView.OutputView;
import store.view.outputView.ProductView;

public class ApplicationConfig {

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(outputView());
    }

    public OutputView outputView() {
        return new OutputView(new ProductView());
    }
}
