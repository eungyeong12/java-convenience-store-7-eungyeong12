package store.constant;

public enum FileName {
    PRODUCT_FILE("products.md");

    private final String name;

    FileName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
