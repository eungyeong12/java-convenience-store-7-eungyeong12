package store.domain.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Products {
    private final List<Product> products;

    private Products(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public static Products of(final List<String> contents) {
        List<Product> products = IntStream.range(1, contents.size())
                .mapToObj(i -> Product.of(contents.get(i)))
                .toList();

        return new Products(products);
    }
}
