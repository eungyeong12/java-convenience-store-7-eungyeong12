package store.config;

import store.controller.ConvenienceStoreController;

public class ApplicationConfig {

    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController();
    }
}
