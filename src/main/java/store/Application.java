package store;

import store.config.ApplicationConfig;
import store.controller.ConvenienceStoreController;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreController convenienceStoreController = new ApplicationConfig().convenienceStoreController();
        convenienceStoreController.run();
    }
}
