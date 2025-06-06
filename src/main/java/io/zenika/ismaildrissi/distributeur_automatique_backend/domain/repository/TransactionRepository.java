package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;

import java.util.Optional;

public interface TransactionRepository {
    void save(Transaction transaction);

    Optional<Transaction> findById(String transactionId);

    void deleteById(String transactionId);

    boolean existsById(String transactionId);
}
