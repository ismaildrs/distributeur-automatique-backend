package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    public List<ProductDTO> listProducts();
    public Optional<ProductDTO> getProductById(UUID productId);

}
