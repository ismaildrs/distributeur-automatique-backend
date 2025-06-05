package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

public class IllegalProductQuantity extends RuntimeException {
    public IllegalProductQuantity(String message) {
        super(message);
    }
}
