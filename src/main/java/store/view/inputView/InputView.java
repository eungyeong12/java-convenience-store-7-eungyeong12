package store.view.inputView;

import camp.nextstep.edu.missionutils.Console;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.validator.Validator;

public class InputView {
    private static final String PRODUCT_AND_QUANTITY_INPUT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String BENEFIT_GUIDE_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String DISCOUNT_GUIDE_MESSAGE = "현재 %s %,d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_GUIDE_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";

    public String getProductAndQuantity() {
        System.out.println(System.lineSeparator() + PRODUCT_AND_QUANTITY_INPUT_MESSAGE);
        return Console.readLine().trim();
    }

    public String getBenefitDecision(Product product) {
        System.out.printf(System.lineSeparator() + BENEFIT_GUIDE_MESSAGE + System.lineSeparator(),
                product.getName());
        return getInput();
    }

    public String getDiscountDecision(PromotionProduct product, int discountNotPossible) {
        System.out.printf(System.lineSeparator() + DISCOUNT_GUIDE_MESSAGE + System.lineSeparator(),
                product.getName(), discountNotPossible);
        return getInput();
    }

    public boolean isMembershipDiscount() {
        System.out.println(System.lineSeparator() + MEMBERSHIP_GUIDE_MESSAGE);
        return getInput().equalsIgnoreCase("Y");
    }

    private String getInput() {
        String input = Console.readLine().trim();
        Validator.validateInputYOrN(input);
        return input;
    }
}
