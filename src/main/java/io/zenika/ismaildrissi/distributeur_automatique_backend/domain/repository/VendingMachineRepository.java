package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing product persistence in the vending machine system.
 *
 * <p>This repository provides the contract for data access operations related to
 * products in the vending machine. It follows the Repository pattern from Domain-Driven
 * Design, abstracting the persistence layer from the domain layer.</p>
 *
 * <p>The repository interface is defined in the domain layer but implemented in the
 * infrastructure layer, ensuring that the domain remains independent of specific
 * persistence technologies.</p>
 *
 * <p>Operations supported:
 * <ul>
 *   <li>Finding products by ID or retrieving all products</li>
 *   <li>Saving new products to the system</li>
 *   <li>Updating existing product information (typically quantity changes)</li>
 *   <li>Deleting products from the system</li>
 *   <li>Checking product existence</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public interface VendingMachineRepository {

    /**
     * Finds a product by its unique identifier.
     *
     * @param productId the unique identifier of the product
     * @return an Optional containing the product if found, empty otherwise
     */
    Optional<Product> findProductById(ProductId productId);

    /**
     * Retrieves all products available in the vending machine.
     *
     * @return a list of all products
     */
    List<Product> findAllProducts();

    /**
     * Saves a new product to the repository.
     *
     * @param product the product to save
     * @return the saved product
     */
    Product saveProduct(Product product);

    /**
     * Updates an existing product in the repository.
     *
     * <p>This method is typically used to update product quantities after
     * dispensing or restocking operations.</p>
     *
     * @param product the product with updated information
     * @return the updated product
     */
    Product updateProduct(Product product);

    /**
     * Deletes a product from the repository by its identifier.
     *
     * @param productId the unique identifier of the product to delete
     */
    void deleteProductById(ProductId productId);

    /**
     * Checks if a product exists in the repository.
     *
     * @param productId the unique identifier of the product to check
     * @return true if the product exists, false otherwise
     */
    boolean productExistsById(ProductId productId);
}
