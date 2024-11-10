package store.domain.promotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import store.exception.ConvenienceStoreException;

public class Promotions {
    private final List<Promotion> promotions;

    private Promotions(List<Promotion> promotions) {
        this.promotions = new ArrayList<>(promotions);
    }

    public static Promotions of(List<String> contents) {
        List<Promotion> promotions = IntStream.range(1, contents.size())
                .mapToObj(i -> Promotion.of(contents.get(i)))
                .toList();

        return new Promotions(promotions);
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

    public Promotion getPromotion(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(promotionName)) {
                return promotion;
            }
        }
        throw new ConvenienceStoreException("프로모션 없음");
    }
}
