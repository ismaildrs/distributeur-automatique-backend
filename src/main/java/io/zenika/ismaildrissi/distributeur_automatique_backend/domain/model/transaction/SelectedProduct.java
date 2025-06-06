package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;

/**
 * Represents a product that has been selected for purchase in a transaction.
 *
 * <p>This value object captures the state of a product at the time of selection,
 * including the price at that moment. This design allows the system to maintain
 * historical pricing information even if the product's current price changes
 * after selection but before transaction completion.</p>
 *
 * <p>The SelectedProduct is immutable and serves as a snapshot of the product
 * information at the time of selection, ensuring transaction integrity and
 * enabling proper audit trails.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class SelectedProduct {

    /** The unique identifier of the selected product */
    private final ProductId productId;

    /** The name of the product at the time of selection */
    private final String name;

    /**
     * The price of the product at the time of selection.
     * This preserves the original price even if the product price changes later.
     */
    private final double priceAtSelection;

    /**
     * Constructs a SelectedProduct with explicit values.
     *
     * @param productId the unique identifier of the product
     * @param name the name of the product
     * @param price the price at the time of selection
     */
    public SelectedProduct(ProductId productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.priceAtSelection = price;
    }

    /**
     * Constructs a SelectedProduct from a Product instance.
     * This captures the current state of the product at selection time.
     *
     * @param product the product being selected
     */
    public SelectedProduct(Product product) {
        this.productId = product.productId();
        this.priceAtSelection = product.price();
        this.name = product.name();
    }

    /**
     * Returns the unique identifier of the selected product.
     *
     * @return the product ID
     */
    public ProductId productId() {
        return productId;
    }

    /**
     * Returns the price of the product at the time of selection.
     *
     * @return the price at selection time
     */
    public double priceAtSelection() {
        return priceAtSelection;
    }

    /**
     * Returns the name of the selected product.
     *
     * @return the product name
     */
    public String name() {
        return name;
    }
}
