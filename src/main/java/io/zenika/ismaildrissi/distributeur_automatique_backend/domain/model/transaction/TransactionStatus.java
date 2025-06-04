package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

/**
 * Represents the state of a transaction, to ensure traceability
 */
public enum TransactionStatus {
    IN_PROGRESS,
    COMPLETED,
    CANCELLED,
}
