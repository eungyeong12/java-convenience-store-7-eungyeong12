package store.config;

import store.controller.CalculationController;
import store.controller.ConvenienceStoreController;
import store.service.CalculatorService;
import store.view.inputView.InputView;
import store.view.outputView.OutputView;
import store.view.outputView.ProductView;
import store.view.outputView.ReceiptView;
import store.view.outputView.receipt.FreeGiftProductsView;
import store.view.outputView.receipt.PriceView;
import store.view.outputView.receipt.PurchaseProductsView;

public class ApplicationConfig {

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(new InputView(), outputView());
    }

    public CalculationController calculationController() {
        return new CalculationController(new InputView(), outputView(), new CalculatorService());
    }

    public OutputView outputView() {
        return new OutputView(new ProductView(), receiptView());
    }

    public ReceiptView receiptView() {
        return new ReceiptView(new PurchaseProductsView(), new FreeGiftProductsView(), new PriceView());
    }
}
