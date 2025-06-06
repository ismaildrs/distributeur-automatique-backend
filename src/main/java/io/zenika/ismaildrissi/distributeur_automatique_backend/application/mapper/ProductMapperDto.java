package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapperDto {

    ModelMapper modelMapper;

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.productId().id());
        productDTO.setName(product.name());
        productDTO.setPrice(product.price());
        productDTO.setQuantity(product.quantity());
        return productDTO;
    }

    public Product toDomain(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
