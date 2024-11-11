package store.exception.promotion;

import store.exception.ConvenienceStoreException;

public class PromotionException extends ConvenienceStoreException {
    private static final String invalidValue = " (존재하지 않는 프로모션: %s)";

    public PromotionException(PromotionErrorMessage message, String input) {
        super(message.getMessage() + String.format(invalidValue, input));
    }
}
