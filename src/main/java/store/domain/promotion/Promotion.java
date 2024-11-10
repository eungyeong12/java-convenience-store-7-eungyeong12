package store.domain.promotion;

import java.util.Date;
import java.util.List;
import store.domain.product.Quantity;
import store.util.Delimiter;

public class Promotion {
    private final PromotionName name;
    private final BuyCount buyCount;
    private final GetCount getCount;
    private final StartDate startDate;
    private final EndDate endDate;

    private Promotion(PromotionName name, BuyCount buyCount, GetCount getCount, StartDate startDate, EndDate endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(String content) {
        List<String> tokens = Delimiter.splitWithComma(content);
        PromotionName name = PromotionName.of(tokens.get(0));
        BuyCount buyCount = BuyCount.of(tokens.get(1));
        GetCount getCount = GetCount.of(tokens.get(2));
        StartDate startDate = StartDate.of(tokens.get(3));
        EndDate endDate = EndDate.of(tokens.get(4));
        return new Promotion(name, buyCount, getCount, startDate, endDate);
    }

    public boolean isBenefitAvailable(int promotionQuantity, Quantity buyQuantity) {
        if (promotionQuantity >= buyQuantity.getQuantity() + 1) {
            return buyQuantity.getQuantity() % (buyCount.getCount() + getCount.getCount()) == buyCount.getCount();
        }
        return false;
    }

    public int getDiscountNotPossible(Quantity buyQuantity) {
        return buyQuantity.getQuantity() % (buyCount.getCount() + getCount.getCount());
    }

    public String getName() {
        return name.getName();
    }

    public int getBuyCount() {
        return buyCount.getCount();
    }

    public int getGetCount() {
        return getCount.getCount();
    }

    public Date getStartDate() {
        return startDate.getDate();
    }

    public Date getEndDate() {
        return endDate.getDate();
    }
}
