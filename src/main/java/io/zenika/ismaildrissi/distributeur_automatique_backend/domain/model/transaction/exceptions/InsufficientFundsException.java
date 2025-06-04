package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
