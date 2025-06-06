package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {

    private VendingMachine vendingMachine;
    private Product product1;
    private Product product2;
    private ProductId productId1;
    private ProductId productId2;

    @BeforeEach
    void setUp() {
        productId1 = new ProductId(UUID.randomUUID());
        productId2 = new ProductId(UUID.randomUUID());
        product1 = new Product("Coke", 1.5, productId1, 5);
        product2 = new Product("Water", 1.0, productId2, 3);
        
        List<Product> products = Arrays.asList(product1, product2);
        List<Money> initialChange = Arrays.asList(
            new Money(10.0), new Money(10.0),
            new Money(5.0), new Money(5.0),
            new Money(2.0), new Money(2.0),
            new Money(1.0), new Money(1.0),
            new Money(0.5), new Money(0.5)
        );
        
        vendingMachine = new VendingMachine(products, initialChange);
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> allProducts = vendingMachine.getAllProducts();
        assertEquals(2, allProducts.size());
        assertTrue(allProducts.contains(product1));
        assertTrue(allProducts.contains(product2));
    }

    @Test
    void shouldReturnProductById() {
        Product retrievedProduct = vendingMachine.getProduct(productId1);
        assertEquals(product1, retrievedProduct);
    }

    @Test
    void shouldThrowExceptionForNonExistentProduct() {
        ProductId nonExistentId = new ProductId(UUID.randomUUID());
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> vendingMachine.getProduct(nonExistentId)
        );
        assertEquals("Product was not found", exception.getMessage());
    }

    @Test
    void shouldReturnTrueForAvailableProduct() {
        assertTrue(vendingMachine.isProductAvailable(productId1));
        assertTrue(vendingMachine.isProductAvailable(productId2));
    }

    @Test
    void shouldReturnFalseForUnavailableProduct() {
        ProductId unavailableId = new ProductId(UUID.randomUUID());
        assertFalse(vendingMachine.isProductAvailable(unavailableId));
    }

    @Test
    void shouldDispenseProductSuccessfully() {
        int initialQuantity = product1.quantity();
        vendingMachine.dispenseProduct(productId1);
        assertEquals(initialQuantity - 1, product1.quantity());
    }

    @Test
    void shouldThrowExceptionWhenDispensingUnavailableProduct() {
        ProductId unavailableId = new ProductId(UUID.randomUUID());
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> vendingMachine.dispenseProduct(unavailableId)
        );
        assertEquals("Product not available", exception.getMessage());
    }

    @Test
    void shouldCalculateChangeCorrectly() {
        List<Money> change = vendingMachine.calculateChange(3.5);
        
        assertFalse(change.isEmpty());
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(3.5, totalChange, 0.01);
    }

    @Test
    void shouldReturnEmptyListWhenCannotMakeExactChange() {
        List<Money> change = vendingMachine.calculateChange(0.3);
        assertTrue(change.isEmpty());
    }

    @Test
    void shouldReturnTrueWhenCanDispenseChange() {
        assertTrue(vendingMachine.canDispenseChange(3.5));
    }

    @Test
    void shouldReturnFalseWhenCannotDispenseChange() {
        assertFalse(vendingMachine.canDispenseChange(0.3));
    }

    @Test
    void shouldCalculateChangeUsingGreedyAlgorithm() {
        List<Money> change = vendingMachine.calculateChange(7.5);
        
        assertFalse(change.isEmpty());
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(7.5, totalChange, 0.01);
        
        long fiveCount = change.stream().filter(m -> m.value() == 5.0).count();
        long twoCount = change.stream().filter(m -> m.value() == 2.0).count();
        long halfCount = change.stream().filter(m -> m.value() == 0.5).count();
        
        assertTrue(fiveCount >= 1);
        assertTrue(twoCount >= 1);
        assertTrue(halfCount >= 1);
    }
}
