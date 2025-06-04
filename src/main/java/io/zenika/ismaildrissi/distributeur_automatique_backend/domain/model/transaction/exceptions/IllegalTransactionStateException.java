package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

public class IllegalTransactionStateException extends RuntimeException {
    public IllegalTransactionStateException(String message) {
        super(message);
    }
}
