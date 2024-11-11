package store.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.constant.FileName;
import store.domain.product.Products;
import store.exception.ConvenienceStoreException;
import store.util.FileReader;

class PurchasedProductsTest {

    @Test
    @DisplayName("구입 상품 입력 성공 테스트")
    void 구입_상품_입력_성공_테스트() {
        //given
        String input = "[비타민워터-3],[물-2],[정식도시락-2]";

        //when
        PurchasedProducts purchasedProducts = PurchasedProducts.of(input,
                Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName())));

        //then
        assertThat(purchasedProducts.getProducts()).hasSize(3);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPurchaseAmount")
    @DisplayName("구입 상품 입력 실패 테스트")
    void 구입_상품_입력_실패_테스트(String input) {
        assertThatThrownBy(() -> PurchasedProducts.of(input,
                Products.of(FileReader.getContents(FileName.PRODUCT_FILE.getName()))))
                .isInstanceOf(ConvenienceStoreException.class);
    }

    private static Stream<Arguments> provideInvalidPurchaseAmount() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("비타민워터-3"),
                Arguments.of("[비타민워터-3][물-2]"),
                Arguments.of("[사과-3]"),
                Arguments.of("[비타민워터-100]")
        );
    }

}