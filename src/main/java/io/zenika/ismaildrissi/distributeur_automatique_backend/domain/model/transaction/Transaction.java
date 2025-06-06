package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;


import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.ChangeCalculator;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.IllegalTransactionStateException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InsufficientFundsException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.impl.ChangeCalculatorImpl;

import java.util.*;

/**
 * Represents a transaction in the vending machine system.
 *
 * <p>A transaction encapsulates the entire purchase process, including money insertion,
 * product selection, and final completion or cancellation. The transaction maintains
 * its state throughout the process and enforces business rules to ensure data integrity.</p>
 *
 * <p>The transaction follows a state-based lifecycle:
 * <ul>
 *   <li>IN_PROGRESS: Money can be inserted and products can be selected</li>
 *   <li>COMPLETED: Transaction finished successfully with products dispensed</li>
 *   <li>CANCELLED: Transaction cancelled with money returned</li>
 * </ul></p>
 *
 * <p>Key business rules enforced:
 * <ul>
 *   <li>Products can only be added if sufficient funds are available</li>
 *   <li>Operations are only allowed on IN_PROGRESS transactions</li>
 *   <li>Completion requires sufficient funds for all selected products</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class Transaction {

    /** Unique identifier for this transaction */
    String id;

    /** List of money denominations inserted during this transaction */
    List<Money> insertedMoney;

    /** List of products selected for purchase */
    List<SelectedProduct> selectedProducts;

    /** Set of products that have been modified and need repository updates */
    Set<Product> updatedProducts;

    /** Current status of the transaction */
    TransactionStatus status;

    /**
     * Constructs a new transaction with the specified ID.
     * The transaction starts in IN_PROGRESS status with empty collections.
     *
     * @param id the unique identifier for this transaction
     */
    public Transaction(String id) {
        this.id = id;
        this.selectedProducts = new ArrayList<>();
        this.insertedMoney = new ArrayList<>();
        this.updatedProducts = new HashSet<>();
        this.status = TransactionStatus.IN_PROGRESS;
    }

    /**
     * Adds a product to the transaction if sufficient funds are available.
     *
     * @param product the product to add to the transaction
     * @throws IllegalTransactionStateException if the transaction is not in progress
     * @throws InsufficientFundsException if there are insufficient funds for the product
     */
    public void addProduct(Product product) {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot add products to a completed transaction.");
        }
        if (product.price() > totalInsertedAmount()) {
            throw new InsufficientFundsException("Cannot add product due to insufficient funds.");
        }
        selectedProducts.add(new SelectedProduct(product));
        updatedProducts.add(product);
    }

    /**
     * Removes a product from the transaction.
     *
     * @param product the product to remove from the transaction
     * @throws IllegalTransactionStateException if the transaction is not in progress
     */
    public void removeProduct(Product product) {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot remove products from a completed transaction.");
        }
        for (SelectedProduct selectedProduct : selectedProducts) {
            if(selectedProduct.productId().equals(product.productId())) {
                selectedProducts.remove(selectedProduct);
                return;
            }
        }
        updatedProducts.add(product);
    }

    /**
     * Inserts money into the transaction.
     *
     * @param money the money denomination to insert
     * @throws IllegalTransactionStateException if the transaction is not in progress
     */
    public void insertMoney(Money money) {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot insert money into a completed transaction.");
        }
        insertedMoney.add(money);
    }

    /**
     * Calculates the total amount of money inserted into the transaction.
     *
     * @return the sum of all inserted money denominations
     */
    private double totalInsertedAmount() {
        return insertedMoney.stream()
                .map(Money::value)
                .reduce(0.0, Double::sum);
    }

    /**
     * Calculates the change amount (inserted amount minus total price).
     *
     * @return the change amount, positive if overpaid, negative if underpaid
     */
    private Double totalCost() {
        return totalInsertedAmount() - totalPrice();
    }

    /**
     * Calculates the total price of all selected products.
     *
     * @return the sum of prices of all selected products
     */
    private double totalPrice() {
        return selectedProducts.stream()
                .map(SelectedProduct::priceAtSelection)
                .reduce(0.0, Double::sum);
    }

    /**
     * Completes the transaction if sufficient funds are available.
     *
     * @return a TransactionResult containing the transaction details
     * @throws IllegalTransactionStateException if the transaction is not in progress
     * @throws InsufficientFundsException if there are insufficient funds
     */
    public TransactionResult complete() {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Transaction is already completed.");
        }
        double totalCost = totalCost();
        if (totalCost < 0){
            throw new InsufficientFundsException("Inserted amount is less than the total price.");
        }
        status = TransactionStatus.COMPLETED;
        return new TransactionResult(selectedProducts(), insertedMoney(), totalPrice(), updatedProducts);
    }

    /**
     * Cancels the transaction and returns all inserted money.
     *
     * @return a TransactionResult with empty products and all inserted money
     * @throws IllegalTransactionStateException if the transaction is not in progress
     */
    public TransactionResult cancel() {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot cancel a completed transaction.");
        }
        status = TransactionStatus.CANCELLED;
        return new TransactionResult(Collections.emptyList(), insertedMoney(), 0.0, updatedProducts);
    }

    /**
     * Returns the unique identifier of this transaction.
     *
     * @return the transaction ID
     */
    public String id() {
        return id;
    }

    /**
     * Returns the current status of this transaction.
     *
     * @return the transaction status
     */
    public TransactionStatus status() {
        return status;
    }

    /**
     * Returns an unmodifiable view of the selected products.
     *
     * @return an unmodifiable list of selected products
     */
    public List<SelectedProduct> selectedProducts() {
        return Collections.unmodifiableList(selectedProducts);
    }

    /**
     * Returns an unmodifiable view of the inserted money.
     *
     * @return an unmodifiable list of inserted money denominations
     */
    public List<Money> insertedMoney() {
        return Collections.unmodifiableList(insertedMoney);
    }

}
