package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a product in the vending machine system.
 *
 * <p>This is a value object that wraps a UUID to provide type safety and ensure
 * that product identifiers cannot be confused with other UUID-based identifiers
 * in the system.</p>
 *
 * <p>The ProductId is immutable and guarantees that the underlying UUID is never null.</p>
 *
 * @param id the unique identifier as a UUID, must not be null
 *
 * @throws NullPointerException if the id parameter is null
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public record ProductId (UUID id) {

    /**
     * Compact constructor that validates the UUID is not null.
     *
     * @throws NullPointerException if id is null
     */
    public ProductId {
        Objects.requireNonNull(id, "Product id cannot be null");
    }

}
