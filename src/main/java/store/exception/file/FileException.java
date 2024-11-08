package store.exception.file;

import store.exception.ConvenienceStoreException;

public class FileException extends ConvenienceStoreException {

    public FileException(FileErrorMessage message) {
        super(message.getMessage());
    }
}
