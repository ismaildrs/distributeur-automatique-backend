package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TransactionResultTest {

    private TransactionResult transactionResult;
    private List<SelectedProduct> selectedProducts;
    private List<Money> insertedMoney;
    private Set<Product> updateProducts;
    private Product product;
    private SelectedProduct selectedProduct;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", 2.0, new ProductId(UUID.randomUUID()), 5);
        selectedProduct = new SelectedProduct(product);
        selectedProducts = Arrays.asList(selectedProduct);
        insertedMoney = Arrays.asList(new Money(5.0));
        updateProducts = new HashSet<>(Arrays.asList(product));
        
        transactionResult = new TransactionResult(selectedProducts, insertedMoney, 2.0, updateProducts);
    }

    @Test
    void shouldCreateTransactionResultWithAllFields() {
        assertNotNull(transactionResult);
        assertEquals(selectedProducts, transactionResult.selectedProducts());
        assertEquals(insertedMoney, transactionResult.insertedMoney());
        assertEquals(2.0, transactionResult.totalPrice());
        assertEquals(updateProducts, transactionResult.updateProducts());
    }

    @Test
    void shouldReturnCorrectSelectedProducts() {
        List<SelectedProduct> result = transactionResult.selectedProducts();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(selectedProduct, result.get(0));
    }

    @Test
    void shouldReturnCorrectInsertedMoney() {
        List<Money> result = transactionResult.insertedMoney();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(5.0, result.get(0).value());
    }

    @Test
    void shouldReturnCorrectTotalPrice() {
        Double result = transactionResult.totalPrice();
        
        assertNotNull(result);
        assertEquals(2.0, result);
    }

    @Test
    void shouldReturnCorrectUpdateProducts() {
        Set<Product> result = transactionResult.updateProducts();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(product));
    }

    @Test
    void shouldCreateTransactionResultWithEmptyCollections() {
        TransactionResult emptyResult = new TransactionResult(
            Collections.emptyList(),
            Collections.emptyList(),
            0.0,
            Collections.emptySet()
        );
        
        assertNotNull(emptyResult);
        assertTrue(emptyResult.selectedProducts().isEmpty());
        assertTrue(emptyResult.insertedMoney().isEmpty());
        assertEquals(0.0, emptyResult.totalPrice());
        assertTrue(emptyResult.updateProducts().isEmpty());
    }

    @Test
    void shouldBeEqualForSameValues() {
        TransactionResult anotherResult = new TransactionResult(selectedProducts, insertedMoney, 2.0, updateProducts);
        
        assertEquals(transactionResult, anotherResult);
        assertEquals(transactionResult.hashCode(), anotherResult.hashCode());
    }

    @Test
    void shouldNotBeEqualForDifferentValues() {
        TransactionResult differentResult = new TransactionResult(
            Collections.emptyList(),
            insertedMoney,
            2.0,
            updateProducts
        );
        
        assertNotEquals(transactionResult, differentResult);
    }
}
