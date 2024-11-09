package store.domain.promotion;

import java.util.List;
import store.util.Delimiter;

public class Promotion {
    private final PromotionName name;
    private final Buy buy;
    private final Get get;
    private final StartDate startDate;
    private final EndDate endDate;

    private Promotion(PromotionName name, Buy buy, Get get, StartDate startDate, EndDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(String content) {
        List<String> tokens = Delimiter.splitWithComma(content);
        PromotionName name = PromotionName.of(tokens.get(0));
        Buy buy = Buy.of(tokens.get(1));
        Get get = Get.of(tokens.get(2));
        StartDate startDate = StartDate.of(tokens.get(3));
        EndDate endDate = EndDate.of(tokens.get(4));
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
