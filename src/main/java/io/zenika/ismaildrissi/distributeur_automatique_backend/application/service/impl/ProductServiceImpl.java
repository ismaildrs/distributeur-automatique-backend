package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.ProductMapperDto;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService interface providing product management operations.
 *
 * <p>This service implementation acts as a bridge between the presentation layer
 * and the domain layer, handling product retrieval operations and data transformation.
 * It coordinates with the VendingMachineRepository to access product data and uses
 * the ProductMapperDto to convert between domain objects and DTOs.</p>
 *
 * <p>The service is transactional to ensure data consistency and is designed to
 * handle all product-related business operations in the application layer.</p>
 *
 * <p>Key responsibilities:
 * <ul>
 *   <li>Retrieving all products from the vending machine</li>
 *   <li>Finding specific products by their unique identifiers</li>
 *   <li>Converting domain objects to DTOs for presentation layer consumption</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    /** Repository for accessing vending machine and product data */
    VendingMachineRepository productRepository;

    /** Mapper for converting between Product domain objects and ProductDTOs */
    ProductMapperDto productMapper;

    /**
     * Retrieves all products available in the vending machine.
     *
     * <p>This method fetches all products from the repository and converts them
     * to DTOs suitable for presentation layer consumption.</p>
     *
     * @return a list of ProductDTO objects representing all available products
     */
    @Override
    public List<ProductDTO> listProducts() {
        return productRepository.findAllProducts().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a specific product by its unique identifier.
     *
     * <p>This method searches for a product with the given UUID and returns
     * it as a DTO if found. The UUID is converted to a ProductId domain object
     * for repository interaction.</p>
     *
     * @param productId the unique identifier of the product to retrieve
     * @return an Optional containing the ProductDTO if found, empty otherwise
     */
    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return productRepository.findProductById(new ProductId(productId)).map(productMapper::toDTO);
    }
}
