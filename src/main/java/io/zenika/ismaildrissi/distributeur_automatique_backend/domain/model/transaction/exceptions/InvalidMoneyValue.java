package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

public class InvalidMoneyValue extends RuntimeException {
    public InvalidMoneyValue(String message) {
        super(message);
    }
}
