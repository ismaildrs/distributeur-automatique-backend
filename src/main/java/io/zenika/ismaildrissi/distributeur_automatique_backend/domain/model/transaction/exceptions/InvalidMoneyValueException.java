package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

public class InvalidMoneyValueException extends RuntimeException {
    public InvalidMoneyValueException(String message) {
        super(message);
    }
}
