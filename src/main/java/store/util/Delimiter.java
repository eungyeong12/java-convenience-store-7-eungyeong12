package store.util;

import java.util.Arrays;
import java.util.List;

public class Delimiter {
    private static final String Comma = ",";
    private static final String DASH = "-";

    public static List<String> splitWithComma(String input) {
        return Arrays.stream(input.split(Comma)).toList();
    }

    public static List<String> splitWithDash(String input) {
        return Arrays.stream(input.split(DASH)).toList();
    }
}
