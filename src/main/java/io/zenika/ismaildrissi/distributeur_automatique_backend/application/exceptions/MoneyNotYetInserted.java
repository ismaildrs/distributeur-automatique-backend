package io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions;

public class MoneyNotYetInserted extends RuntimeException {
    public MoneyNotYetInserted(String message) {
        super(message);
    }
}
