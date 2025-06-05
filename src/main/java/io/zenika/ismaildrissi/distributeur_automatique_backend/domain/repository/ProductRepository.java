package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(ProductId productId);

    List<Product> findAll();

    Product save(Product product);

    void deleteById(ProductId productId);

    boolean existsById(ProductId productId);
}
