package store.domain.promotion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        this.promotions = new ArrayList<>(promotions);
    }

    public static Promotions of(final List<String> contents) {
        List<Promotion> promotions = IntStream.range(1, contents.size())
                .mapToObj(i -> Promotion.of(contents.get(i)))
                .toList();

        return new Promotions(promotions);
    }
}
