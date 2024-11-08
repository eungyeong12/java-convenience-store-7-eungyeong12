package store.util;

import java.util.Arrays;
import java.util.List;

public class Delimiter {
    private static final String DELIMITER = ",";

    public static List<String> splitWithDelimiter(String input) {
        return Arrays.stream(input.split(DELIMITER)).toList();
    }
}
