package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper.ProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.jpa.SpringDataProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class VendingMachineRepositoryImpl implements VendingMachineRepository {
    SpringDataProductRepository springDataProductRepository;
    ProductMapper productMapper;

    @Override
    public Optional<Product> findProductById(ProductId productId) {
        return springDataProductRepository.findById(productId.id())
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAllProducts() {
        return springDataProductRepository.findAll().stream().map(productMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Product saveProduct(Product product) {
        return productMapper.toDomain(springDataProductRepository.save(productMapper.toEntity(product)));
    }

    @Override
    public void deleteProductById(ProductId productId) {
        springDataProductRepository.deleteById(productId.id());
    }

    @Override
    public boolean productExistsById(ProductId productId) {
        return springDataProductRepository.existsById(productId.id());
    }

    @Override
    public Product updateProduct(Product product) {
        return productMapper.toDomain(springDataProductRepository.save(productMapper.toEntity(product)));
    }
}