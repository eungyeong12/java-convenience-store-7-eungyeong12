package store.exception;

public class ConvenienceStoreException extends IllegalArgumentException {
    private static final String ERROR_PREFIX = "[ERROR] ";

    public ConvenienceStoreException(String message) {
        super(ERROR_PREFIX + message);
    }
}
