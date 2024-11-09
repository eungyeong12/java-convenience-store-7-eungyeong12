package store.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.FileName;
import store.domain.product.Products;
import store.util.FileReader;

class PurchasedProductsTest {

    @Test
    @DisplayName("성공 테스트")
    void 구입_상품_입력_성공_테스트() {
        //given
        String input = "[비타민워터-3],[물-2],[정식도시락-2]";

        //when
        PurchasedProducts purchasedProducts = PurchasedProducts.of(input,
                Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName())));

        //then
        assertThat(purchasedProducts.getProducts()).hasSize(3);
    }
}