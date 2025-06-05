package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.exceptions.IllegalProductQuantity;

/**
 * Represents an actual product in our vending machine
 */
public class Product {
    private final ProductId productId;
    private final String name;
    private final double price;
    private int quantity;

    public Product(String name, double price, ProductId productId, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int quantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void decreaseQuantity() {
        if (quantity <= 0) {
            throw new IllegalProductQuantity("Cannot decrease quantity. Product out of stock.");
        }
        quantity--;
    }
}
