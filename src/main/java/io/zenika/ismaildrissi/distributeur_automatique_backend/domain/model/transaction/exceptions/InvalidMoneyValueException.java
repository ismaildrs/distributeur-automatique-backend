package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

/**
 * Exception thrown when an invalid monetary value is provided to the Money domain object.
 *
 * <p>This exception is raised when attempting to create a Money instance with:
 * <ul>
 *   <li>A negative or zero value</li>
 *   <li>A value that is not a valid MAD coin denomination (0.5, 1.0, 2.0, 5.0, 10.0)</li>
 * </ul></p>
 *
 * <p>This is a domain-level exception that enforces business rules about valid
 * monetary values in the vending machine system.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class InvalidMoneyValueException extends RuntimeException {

    /**
     * Constructs a new InvalidMoneyValueException with the specified detail message.
     *
     * @param message the detail message explaining why the money value is invalid
     */
    public InvalidMoneyValueException(String message) {
        super(message);
    }
}
