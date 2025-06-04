package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product;

/**
 * Represents an actual product in our vending machine
 */
public class Product {
    private final ProductId productId;
    private final String name;
    private final double price;


    public Product(String name, double price, ProductId productId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public ProductId productId() {
        return productId;
    }

    public String name() {
        return name;
    }

    public double price() {
        return price;
    }
}
