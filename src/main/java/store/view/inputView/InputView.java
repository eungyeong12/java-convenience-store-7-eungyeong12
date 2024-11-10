package store.view.inputView;

import camp.nextstep.edu.missionutils.Console;
import store.domain.product.Product;
import store.exception.ConvenienceStoreException;

public class InputView {
    private static final String PRODUCT_AND_QUANTITY_INPUT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String BENEFIT_INFORMATION_MESSAGE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";

    public String getProductAndQuantity() {
        System.out.println(System.lineSeparator() + PRODUCT_AND_QUANTITY_INPUT_MESSAGE);
        return Console.readLine().trim();
    }

    public String getBenefitDecision(Product product) {
        System.out.printf(System.lineSeparator() + BENEFIT_INFORMATION_MESSAGE + System.lineSeparator(),
                product.getName());
        String answer = Console.readLine().trim();
        if (!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))) {
            throw new ConvenienceStoreException("Y 또는 N으로 입력");
        }
        return answer;
    }
}
