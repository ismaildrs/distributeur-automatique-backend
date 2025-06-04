package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product;

public class Product {
    private final ProductId productId;
    private final String name;
    private final double price;


    public Product(String name, double price, ProductId productId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
