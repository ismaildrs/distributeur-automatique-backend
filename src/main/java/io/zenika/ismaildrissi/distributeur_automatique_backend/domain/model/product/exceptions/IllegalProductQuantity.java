package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.exceptions;

public class IllegalProductQuantity extends RuntimeException {
    public IllegalProductQuantity(String message) {
        super(message);
    }
}
