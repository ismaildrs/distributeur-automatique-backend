package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.ProductRepository;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper.ProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.entity.ProductEntity;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.jpa.SpringDataProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{
    SpringDataProductRepository springDataProductRepository;
    ProductMapper productMapper;

    @Override
    public Optional<Product> findById(ProductId productId) {
        return springDataProductRepository.findById(productId.id())
                .map(productMapper::toDmain);
    }


    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll().stream().map(productMapper::toDmain).collect(Collectors.toList());
    }

    @Override
    public Product save(Product product) {
        return productMapper.toDmain(springDataProductRepository.save(productMapper.toEntity(product)));
    }

    @Override
    public void deleteById(ProductId productId) {
        springDataProductRepository.deleteById(productId.id());
    }

    @Override
    public boolean existsById(ProductId productId) {
        return springDataProductRepository.existsById(productId.id());
    }
}