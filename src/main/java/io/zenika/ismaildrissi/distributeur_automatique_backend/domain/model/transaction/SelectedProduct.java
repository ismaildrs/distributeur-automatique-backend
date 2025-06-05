package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;


/**
 * Note: Should SelectedProduct class have the priceAtSelection attribute ?
 * Are we intending to keep track of Purchases (Transactions) ?
 */

public class SelectedProduct {
    private final ProductId productId;
    /**
     * priceAtSelection is used to store the price at selling time
     * If price changed later, we could keep track of old prices.
     */
    private final double priceAtSelection;

    public SelectedProduct(Product product){
        this.productId = product.productId();
        this.priceAtSelection = product.price();
    }

    public ProductId productId() {
        return productId;
    }

    public double priceAtSelection() {
        return priceAtSelection;
    }
}
