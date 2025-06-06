package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SelectedProductMapper {

    ModelMapper modelMapper;

    public SelectedProduct toDomain(SelectedProductDTO selectedProductDTO) {
        return new SelectedProduct(new ProductId(selectedProductDTO.getId()), selectedProductDTO.getName(), selectedProductDTO.getPrice());
    }

    public SelectedProductDTO toDto(SelectedProduct selectedProduct) {
        SelectedProductDTO selectedProductDTO = new SelectedProductDTO();
        selectedProductDTO.setId(selectedProduct.productId().id());
        selectedProductDTO.setPrice(selectedProduct.priceAtSelection());
        selectedProductDTO.setName(selectedProduct.name());
        return selectedProductDTO;
    }

}
