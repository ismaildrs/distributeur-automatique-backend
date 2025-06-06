package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;

import java.util.List;
import java.util.Optional;

public interface VendingMachineRepository {
    Optional<Product> findProductById(ProductId productId);

    List<Product> findAllProducts();

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductById(ProductId productId);

    boolean productExistsById(ProductId productId);
}
