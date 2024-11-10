package store.domain.product;

public class Product {
    private final ProductName name;
    private final Price price;
    private final Quantity quantity;

    public Product(ProductName name, Price price, Quantity quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void decreaseStock(Quantity buyQuantity) {
        quantity.decreaseStock(buyQuantity.getQuantity());
    }

    public String getName() {
        return name.getName();
    }

    public int getPrice() {
        return price.getPrice();
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }
}
