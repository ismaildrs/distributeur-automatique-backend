package io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions;

/**
 * Exception thrown when a requested product cannot be found in the vending machine.
 *
 * <p>This exception is raised when:
 * <ul>
 *   <li>Attempting to retrieve a product with an ID that doesn't exist</li>
 *   <li>Attempting to dispense a product that is not available</li>
 *   <li>Attempting to select a product that is out of stock</li>
 * </ul></p>
 *
 * <p>This is an application-level exception that indicates a client request
 * for a non-existent or unavailable product.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProductNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining why the product was not found
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
