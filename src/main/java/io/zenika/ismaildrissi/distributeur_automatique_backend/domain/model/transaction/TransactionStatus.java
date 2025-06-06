package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

/**
 * Represents the lifecycle states of a transaction in the vending machine system.
 *
 * <p>This enumeration defines the possible states that a transaction can be in,
 * ensuring proper state management and traceability throughout the transaction
 * lifecycle. The status helps enforce business rules and prevents invalid
 * operations on transactions.</p>
 *
 * <p>The transaction follows a linear progression:
 * IN_PROGRESS → COMPLETED or IN_PROGRESS → CANCELLED</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public enum TransactionStatus {

    /**
     * The transaction is currently active and can accept money insertions
     * and product selections. This is the initial state of any new transaction.
     */
    IN_PROGRESS,

    /**
     * The transaction has been successfully completed with products dispensed
     * and change returned. This is a terminal state.
     */
    COMPLETED,

    /**
     * The transaction has been cancelled and all inserted money has been returned.
     * This is a terminal state.
     */
    CANCELLED,
}
