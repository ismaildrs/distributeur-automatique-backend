package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.exceptions.IllegalProductQuantity;

/**
 * Represents a product available in the vending machine.
 *
 * <p>This domain entity encapsulates all the essential information about a product
 * including its unique identifier, name, price, and current quantity in stock.
 * The product maintains its own inventory state and provides methods to check
 * availability and manage stock levels.</p>
 *
 * <p>The product is designed to be thread-safe for quantity operations and enforces
 * business rules such as preventing quantity reduction when out of stock.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class Product {

    /** The unique identifier for this product */
    private final ProductId productId;

    /** The display name of the product */
    private final String name;

    /** The price of the product in MAD */
    private final double price;

    /** The current quantity available in the vending machine */
    private int quantity;

    /**
     * Constructs a new Product with the specified details.
     *
     * @param name the display name of the product, should not be null or empty
     * @param price the price of the product in MAD, should be positive
     * @param productId the unique identifier for this product, must not be null
     * @param quantity the initial quantity available, should be non-negative
     */
    public Product(String name, double price, ProductId productId, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Returns the unique identifier of this product.
     *
     * @return the product ID
     */
    public ProductId productId() {
        return productId;
    }

    /**
     * Returns the display name of this product.
     *
     * @return the product name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the price of this product in MAD.
     *
     * @return the product price
     */
    public double price() {
        return price;
    }

    /**
     * Returns the current quantity available in the vending machine.
     *
     * @return the current quantity
     */
    public int quantity() {
        return quantity;
    }

    /**
     * Checks if this product is currently available for purchase.
     *
     * @return true if quantity is greater than 0, false otherwise
     */
    public boolean isAvailable() {
        return quantity > 0;
    }

    /**
     * Decreases the quantity of this product by one unit.
     * This method is typically called when a product is dispensed.
     *
     * @throws IllegalProductQuantity if the product is already out of stock
     */
    public void decreaseQuantity() {
        if (quantity <= 0) {
            throw new IllegalProductQuantity("Cannot decrease quantity. Product out of stock.");
        }
        quantity--;
    }
}
