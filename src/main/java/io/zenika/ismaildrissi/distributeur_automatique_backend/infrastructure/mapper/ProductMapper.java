package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

    ModelMapper modelMapper;

    public Product toDomain(ProductEntity productEntity) {
        Product product = new Product(productEntity.getName(), productEntity.getPrice(), new ProductId(productEntity.getId()), productEntity.getQuantity());
        return product;
    }

    public ProductEntity toEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.productId().id());
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setQuantity(product.quantity());

        return productEntity;
    }

}
