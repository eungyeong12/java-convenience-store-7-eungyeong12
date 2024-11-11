package store.exception.product;

import store.exception.ConvenienceStoreException;

public class ProductException extends ConvenienceStoreException {
    private static final String invalidValue = " (존재하지 않는 상품: %s)";

    public ProductException(ProductErrorMessage message) {
        super(message.getMessage());
    }
    
    public ProductException(ProductErrorMessage message, String input) {
        super(message.getMessage() + String.format(invalidValue, input));
    }
}
