package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.exceptions.IllegalProductQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private ProductId productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = new ProductId(UUID.randomUUID());
        product = new Product("Test Product", 2.5, productId, 5);
    }

    @Test
    void shouldCreateProductWithValidParameters() {
        assertEquals("Test Product", product.name());
        assertEquals(2.5, product.price());
        assertEquals(productId, product.productId());
        assertEquals(5, product.quantity());
    }

    @Test
    void shouldReturnTrueWhenProductIsAvailable() {
        assertTrue(product.isAvailable());
    }

    @Test
    void shouldReturnFalseWhenProductIsNotAvailable() {
        Product outOfStockProduct = new Product("Out of Stock", 1.0, productId, 0);
        assertFalse(outOfStockProduct.isAvailable());
    }

    @Test
    void shouldDecreaseQuantitySuccessfully() {
        int initialQuantity = product.quantity();
        product.decreaseQuantity();
        assertEquals(initialQuantity - 1, product.quantity());
    }

    @Test
    void shouldDecreaseQuantityToZero() {
        Product singleItemProduct = new Product("Single Item", 1.0, productId, 1);
        singleItemProduct.decreaseQuantity();
        assertEquals(0, singleItemProduct.quantity());
        assertFalse(singleItemProduct.isAvailable());
    }

    @Test
    void shouldThrowExceptionWhenDecreasingQuantityOfOutOfStockProduct() {
        Product outOfStockProduct = new Product("Out of Stock", 1.0, productId, 0);
        IllegalProductQuantity exception = assertThrows(
            IllegalProductQuantity.class,
            outOfStockProduct::decreaseQuantity
        );
        assertEquals("Cannot decrease quantity. Product out of stock.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDecreasingQuantityBelowZero() {
        Product singleItemProduct = new Product("Single Item", 1.0, productId, 1);
        singleItemProduct.decreaseQuantity();
        
        IllegalProductQuantity exception = assertThrows(
            IllegalProductQuantity.class,
            singleItemProduct::decreaseQuantity
        );
        assertEquals("Cannot decrease quantity. Product out of stock.", exception.getMessage());
    }
}
