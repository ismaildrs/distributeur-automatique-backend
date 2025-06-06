package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions;

/**
 * Exception thrown when there are insufficient funds to complete a transaction operation.
 *
 * <p>This exception is raised in the following scenarios:
 * <ul>
 *   <li>Attempting to add a product to a transaction when the inserted money is less than the product price</li>
 *   <li>Attempting to complete a transaction when the total inserted amount is less than the total price of selected products</li>
 * </ul></p>
 *
 * <p>This is a domain-level exception that enforces the business rule that customers
 * must insert sufficient money before purchasing products.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class InsufficientFundsException extends RuntimeException {

    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     *
     * @param message the detail message explaining the insufficient funds condition
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
