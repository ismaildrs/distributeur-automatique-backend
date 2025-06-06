package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.IllegalTransactionStateException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InsufficientFundsException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;
    private Product product;
    private Money money;

    @BeforeEach
    void setUp() {
        transaction = new Transaction("test-transaction-id");
        product = new Product("Test Product", 2.0, new ProductId(UUID.randomUUID()), 5);
        money = new Money(5.0);
    }

    @Test
    void shouldCreateTransactionWithCorrectInitialState() {
        assertEquals("test-transaction-id", transaction.id());
        assertEquals(TransactionStatus.IN_PROGRESS, transaction.status());
        assertTrue(transaction.selectedProducts().isEmpty());
        assertTrue(transaction.insertedMoney().isEmpty());
    }

    @Test
    void shouldInsertMoneySuccessfully() {
        transaction.insertMoney(money);
        assertEquals(1, transaction.insertedMoney().size());
        assertEquals(money, transaction.insertedMoney().get(0));
    }

    @Test
    void shouldAddProductWhenSufficientFunds() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        
        assertEquals(1, transaction.selectedProducts().size());
        assertEquals(product.productId(), transaction.selectedProducts().get(0).productId());
    }

    @Test
    void shouldThrowExceptionWhenAddingProductWithInsufficientFunds() {
        InsufficientFundsException exception = assertThrows(
            InsufficientFundsException.class,
            () -> transaction.addProduct(product)
        );
        assertEquals("Cannot add product due to insufficient funds.", exception.getMessage());
    }

    @Test
    void shouldRemoveProductSuccessfully() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.removeProduct(product);
        
        assertTrue(transaction.selectedProducts().isEmpty());
    }

    @Test
    void shouldCompleteTransactionSuccessfully() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        
        TransactionResult result = transaction.complete();
        
        assertEquals(TransactionStatus.COMPLETED, transaction.status());
        assertEquals(1, result.selectedProducts().size());
        assertEquals(1, result.insertedMoney().size());
        assertEquals(2.0, result.totalPrice());
    }

    @Test
    void shouldThrowExceptionWhenAddingProductWithInsufficientFundsAfterInsertion() {
        transaction.insertMoney(new Money(1.0));

        InsufficientFundsException exception = assertThrows(
            InsufficientFundsException.class,
            () -> transaction.addProduct(product)
        );
        assertEquals("Cannot add product due to insufficient funds.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCompletingWithInsufficientTotalFunds() {
        transaction.insertMoney(new Money(1.0));
        Product cheapProduct = new Product("Cheap Item", 0.5, new ProductId(UUID.randomUUID()), 3);
        transaction.addProduct(cheapProduct);
        transaction.addProduct(cheapProduct);
        transaction.addProduct(cheapProduct);

        InsufficientFundsException exception = assertThrows(
            InsufficientFundsException.class,
            () -> transaction.complete()
        );
        assertEquals("Inserted amount is less than the total price.", exception.getMessage());
    }

    @Test
    void shouldCancelTransactionSuccessfully() {
        transaction.insertMoney(money);
        
        TransactionResult result = transaction.cancel();
        
        assertEquals(TransactionStatus.CANCELLED, transaction.status());
        assertTrue(result.selectedProducts().isEmpty());
        assertEquals(1, result.insertedMoney().size());
        assertEquals(0.0, result.totalPrice());
    }

    @Test
    void shouldThrowExceptionWhenInsertingMoneyInCompletedTransaction() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.complete();
        
        IllegalTransactionStateException exception = assertThrows(
            IllegalTransactionStateException.class,
            () -> transaction.insertMoney(new Money(1.0))
        );
        assertEquals("Cannot insert money into a completed transaction.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddingProductToCompletedTransaction() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.complete();
        
        IllegalTransactionStateException exception = assertThrows(
            IllegalTransactionStateException.class,
            () -> transaction.addProduct(product)
        );
        assertEquals("Cannot add products to a completed transaction.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRemovingProductFromCompletedTransaction() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.complete();
        
        IllegalTransactionStateException exception = assertThrows(
            IllegalTransactionStateException.class,
            () -> transaction.removeProduct(product)
        );
        assertEquals("Cannot remove products from a completed transaction.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCompletingAlreadyCompletedTransaction() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.complete();
        
        IllegalTransactionStateException exception = assertThrows(
            IllegalTransactionStateException.class,
            () -> transaction.complete()
        );
        assertEquals("Transaction is already completed.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCancellingCompletedTransaction() {
        transaction.insertMoney(money);
        transaction.addProduct(product);
        transaction.complete();
        
        IllegalTransactionStateException exception = assertThrows(
            IllegalTransactionStateException.class,
            () -> transaction.cancel()
        );
        assertEquals("Cannot cancel a completed transaction.", exception.getMessage());
    }
}
