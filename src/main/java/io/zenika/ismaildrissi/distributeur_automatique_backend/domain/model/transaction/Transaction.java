package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;


import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.ChangeCalculator;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.IllegalTransactionStateException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InsufficientFundsException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.impl.ChangeCalculatorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transaction {
    String id;
    List<Money> insertedMoney;
    List<SelectedProduct> selectedProducts;
    TransactionStatus status;

    ChangeCalculator changeCalculator;

    public Transaction(String id) {
        this.id = id;
        this.selectedProducts = new ArrayList<>();
        this.insertedMoney = new ArrayList<>();
        this.status = TransactionStatus.IN_PROGRESS;
        this.changeCalculator = new ChangeCalculatorImpl();
    }

    public void addProduct(Product product) {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot add products to a completed transaction.");
        } else if (product.price() > totalCost()) {
            selectedProducts.add(new SelectedProduct(product));
        }
    }

    public void insertMoney(Money money) {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot insert money into a completed transaction.");
        }
        insertedMoney.add(money);
    }

    private double totalInsertedAmount() {
        return insertedMoney.stream()
                .map(Money::value)
                .reduce(0.0, Double::sum);
    }

    private Double totalCost() {
        return totalInsertedAmount() - totalPrice();
    }

    private double totalPrice() {
        return selectedProducts.stream()
                .map(SelectedProduct::priceAtSelection)
                .reduce(0.0, Double::sum);
    }

    public TransactionResult complete() {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Transaction is already completed.");
        }
        double totalCost = totalCost();
        if (totalCost >= 0){
            throw new InsufficientFundsException("Inserted amount is less than the total price.");
        }
        status = TransactionStatus.COMPLETED;
        List<Money> returnedMoney = changeCalculator.calculateChange(totalCost);
        return new TransactionResult(selectedProducts(), returnedMoney);
    }

    public TransactionResult cancel() {
        if (status != TransactionStatus.IN_PROGRESS) {
            throw new IllegalTransactionStateException("Cannot cancel a completed transaction.");
        }
        status = TransactionStatus.CANCELLED;
        return new TransactionResult(Collections.emptyList(), insertedMoney());
    }

    public String id() {
        return id;
    }

    public TransactionStatus status() {
        return status;
    }

    public List<SelectedProduct> selectedProducts() {
        return Collections.unmodifiableList(selectedProducts);
    }

    public List<Money> insertedMoney() {
        return Collections.unmodifiableList(insertedMoney);
    }

}
