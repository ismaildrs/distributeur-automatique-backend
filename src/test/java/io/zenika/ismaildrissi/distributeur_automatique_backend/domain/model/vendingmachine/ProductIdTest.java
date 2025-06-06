package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    void shouldCreateProductIdWithValidUUID() {
        UUID uuid = UUID.randomUUID();
        ProductId productId = new ProductId(uuid);
        assertEquals(uuid, productId.id());
    }

    @Test
    void shouldThrowExceptionForNullUUID() {
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new ProductId(null)
        );
        assertEquals("Product id cannot be null", exception.getMessage());
    }

    @Test
    void shouldBeEqualForSameUUID() {
        UUID uuid = UUID.randomUUID();
        ProductId productId1 = new ProductId(uuid);
        ProductId productId2 = new ProductId(uuid);
        assertEquals(productId1, productId2);
        assertEquals(productId1.hashCode(), productId2.hashCode());
    }

    @Test
    void shouldNotBeEqualForDifferentUUIDs() {
        ProductId productId1 = new ProductId(UUID.randomUUID());
        ProductId productId2 = new ProductId(UUID.randomUUID());
        assertNotEquals(productId1, productId2);
    }
}
