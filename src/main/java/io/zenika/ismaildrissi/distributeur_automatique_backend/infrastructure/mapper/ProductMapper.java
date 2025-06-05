package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

    ModelMapper modelMapper;

    public Product toDmain(ProductEntity productEntity) {
        return modelMapper.map(productEntity, Product.class);
    }

    public ProductEntity toEntity(Product product) {
        return modelMapper.map(product, ProductEntity.class);
    }

}
