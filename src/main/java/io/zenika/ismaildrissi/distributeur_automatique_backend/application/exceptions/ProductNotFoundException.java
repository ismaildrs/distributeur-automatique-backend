package io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
