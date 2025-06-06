package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import java.util.Objects;
import java.util.UUID;

/**
 * ProductId is the unique identifier of a Product, It's just a wrapper around UUID
 * @param id
 */
public record ProductId (UUID id) {

    public ProductId {
        Objects.requireNonNull(id, "Product id cannot be null");
    }

}
