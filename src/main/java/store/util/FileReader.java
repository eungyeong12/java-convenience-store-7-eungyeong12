package store.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import store.exception.file.FileErrorMessage;
import store.exception.file.FileException;

public class FileReader {

    public static List<String> getContents(String fileName) {
        try {
            return Files.readAllLines(getPath(fileName));
        } catch (FileNotFoundException e) {
            throw new FileException(FileErrorMessage.FILE_NOT_FOUND);
        } catch (IOException e) {
            throw new FileException(FileErrorMessage.IO_EXCEPTION);
        }
    }

    public static Path getPath(String fileName) {
        URL resource = FileReader.class.getClassLoader().getResource(fileName);
        return new File(Objects.requireNonNull(resource).getPath()).toPath();
    }
}
