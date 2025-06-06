package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between SelectedProduct domain objects and SelectedProductDTO.
 *
 * <p>This mapper handles the bidirectional conversion between the domain layer's
 * SelectedProduct value object and the application layer's SelectedProductDTO.
 * It manages the mapping of complex nested objects like ProductId and ensures
 * proper preservation of pricing information at selection time.</p>
 *
 * <p>The mapper is essential for maintaining the integrity of transaction data,
 * ensuring that product information captured at selection time is accurately
 * represented in the presentation layer.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Component
@AllArgsConstructor
public class SelectedProductMapper {

    /** ModelMapper instance for automated mapping operations */
    ModelMapper modelMapper;

    /**
     * Converts a SelectedProductDTO to a SelectedProduct domain object.
     *
     * <p>This method manually constructs the domain object to ensure proper
     * handling of the ProductId value object and other domain-specific structures.</p>
     *
     * @param selectedProductDTO the DTO to convert
     * @return the corresponding SelectedProduct domain object
     */
    public SelectedProduct toDomain(SelectedProductDTO selectedProductDTO) {
        return new SelectedProduct(new ProductId(selectedProductDTO.getId()), selectedProductDTO.getName(), selectedProductDTO.getPrice());
    }

    /**
     * Converts a SelectedProduct domain object to a SelectedProductDTO.
     *
     * <p>This method manually maps the domain object fields to ensure proper
     * extraction of the UUID from ProductId and preservation of the price
     * at selection time.</p>
     *
     * @param selectedProduct the domain object to convert
     * @return the corresponding SelectedProductDTO
     */
    public SelectedProductDTO toDto(SelectedProduct selectedProduct) {
        SelectedProductDTO selectedProductDTO = new SelectedProductDTO();
        selectedProductDTO.setId(selectedProduct.productId().id());
        selectedProductDTO.setPrice(selectedProduct.priceAtSelection());
        selectedProductDTO.setName(selectedProduct.name());
        return selectedProductDTO;
    }

}
