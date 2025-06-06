package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between Product domain objects and ProductDTO.
 *
 * <p>This mapper handles the bidirectional conversion between the domain layer's
 * Product entity and the application layer's ProductDTO. It manages the mapping
 * of complex nested objects like ProductId and ensures proper data transformation.</p>
 *
 * <p>The mapper uses both manual mapping for complex transformations and ModelMapper
 * for simpler conversions, providing flexibility and maintainability.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Component
@AllArgsConstructor
public class ProductMapperDto {

    /** ModelMapper instance for automated mapping operations */
    ModelMapper modelMapper;

    /**
     * Converts a Product domain object to a ProductDTO.
     *
     * <p>This method manually maps the Product fields to ensure proper handling
     * of the ProductId value object and other domain-specific structures.</p>
     *
     * @param product the domain object to convert
     * @return the corresponding ProductDTO
     */
    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.productId().id());
        productDTO.setName(product.name());
        productDTO.setPrice(product.price());
        productDTO.setQuantity(product.quantity());
        return productDTO;
    }

    /**
     * Converts a ProductDTO to a Product domain object.
     *
     * <p>This method uses ModelMapper for the conversion. Note that this
     * conversion may require additional configuration for complex domain
     * object construction.</p>
     *
     * @param productDTO the DTO to convert
     * @return the corresponding Product domain object
     */
    public Product toDomain(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
