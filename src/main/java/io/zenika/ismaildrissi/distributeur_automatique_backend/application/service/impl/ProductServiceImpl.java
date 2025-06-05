package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductRepositoryImpl implements ProductRepository {
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> listProducts() {
        return productRepository.listProducts();
    }

    @Override
    public ProductDTO getProductById(UUID productId) {
        return null;
    }
}
