package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;


/**
 * Note: Should SelectedProduct class have the priceAtSelection attribute ?
 * Are we intending to keep track of Purchases (Transactions) ?
 */

public class SelectedProduct {
    private final ProductId productId;
    private final String name;
    /**
     * priceAtSelection is used to store the price at selling time
     * If price changed later, we could keep track of old prices.
     */
    private final double priceAtSelection;

    public SelectedProduct(ProductId productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.priceAtSelection = price;
    }

    public SelectedProduct(Product product) {
        this.productId = product.productId();
        this.priceAtSelection = product.price();
        this.name = product.name();
    }

    public ProductId productId() {
        return productId;
    }

    public double priceAtSelection() {
        return priceAtSelection;
    }

    public String name() {
        return name;
    }
}
