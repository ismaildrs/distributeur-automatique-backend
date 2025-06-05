package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SelectedProductMapper {

    ModelMapper modelMapper;

    public SelectedProduct toDomain(SelectedProductDTO selectedProductDTO) {
        return modelMapper.map(selectedProductDTO, SelectedProduct.class);
    }

    public SelectedProductDTO toDto(SelectedProduct selectedProduct) {
        return modelMapper.map(selectedProduct, SelectedProductDTO.class);
    }

}
