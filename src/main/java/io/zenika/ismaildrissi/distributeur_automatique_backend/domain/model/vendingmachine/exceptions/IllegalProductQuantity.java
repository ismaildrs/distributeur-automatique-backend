package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.exceptions;

public class IllegalProductQuantity extends RuntimeException {
    public IllegalProductQuantity(String message) {
        super(message);
    }
}
