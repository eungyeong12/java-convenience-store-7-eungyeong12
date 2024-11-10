package store.config;

import store.controller.ConvenienceStoreController;
import store.view.inputView.InputView;
import store.view.outputView.OutputView;
import store.view.outputView.ProductView;
import store.view.outputView.ReceiptView;

public class ApplicationConfig {

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(new InputView(), outputView());
    }

    public OutputView outputView() {
        return new OutputView(new ProductView(), new ReceiptView());
    }
}
