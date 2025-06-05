package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.ProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    @Override
    public List<ProductDTO> listProducts() {
        return productRepository.findAll().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return productRepository.findById(new ProductId(productId)).map(productMapper::toDTO);
    }
}
