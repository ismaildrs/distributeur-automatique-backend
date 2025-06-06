package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing products in the vending machine system.
 *
 * <p>This service provides operations for retrieving product information
 * from the vending machine. It acts as a facade between the presentation
 * layer and the domain layer, handling data transformation and business
 * logic coordination.</p>
 *
 * <p>All operations return DTOs suitable for presentation layer consumption,
 * abstracting away the complexity of the underlying domain model.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public interface ProductService {

    /**
     * Retrieves all products available in the vending machine.
     *
     * @return a list of all products as DTOs
     */
    public List<ProductDTO> listProducts();

    /**
     * Retrieves a specific product by its unique identifier.
     *
     * @param productId the unique identifier of the product
     * @return an Optional containing the product DTO if found, empty otherwise
     */
    public Optional<ProductDTO> getProductById(UUID productId);

}
