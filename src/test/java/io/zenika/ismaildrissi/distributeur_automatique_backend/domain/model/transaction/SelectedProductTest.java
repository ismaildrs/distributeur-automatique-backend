package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SelectedProductTest {

    private ProductId productId;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = new ProductId(UUID.randomUUID());
        product = new Product("Test Product", 2.5, productId, 5);
    }

    @Test
    void shouldCreateSelectedProductFromProduct() {
        SelectedProduct selectedProduct = new SelectedProduct(product);
        
        assertEquals(productId, selectedProduct.productId());
        assertEquals("Test Product", selectedProduct.name());
        assertEquals(2.5, selectedProduct.priceAtSelection());
    }

    @Test
    void shouldCreateSelectedProductWithParameters() {
        SelectedProduct selectedProduct = new SelectedProduct(productId, "Custom Product", 3.0);
        
        assertEquals(productId, selectedProduct.productId());
        assertEquals("Custom Product", selectedProduct.name());
        assertEquals(3.0, selectedProduct.priceAtSelection());
    }

    @Test
    void shouldPreservePriceAtSelectionTime() {
        SelectedProduct selectedProduct = new SelectedProduct(product);
        double originalPrice = selectedProduct.priceAtSelection();
        
        assertEquals(2.5, originalPrice);
        assertEquals(originalPrice, selectedProduct.priceAtSelection());
    }

    @Test
    void shouldReturnCorrectProductId() {
        SelectedProduct selectedProduct = new SelectedProduct(product);
        assertEquals(productId, selectedProduct.productId());
    }

    @Test
    void shouldReturnCorrectName() {
        SelectedProduct selectedProduct = new SelectedProduct(product);
        assertEquals("Test Product", selectedProduct.name());
    }
}
