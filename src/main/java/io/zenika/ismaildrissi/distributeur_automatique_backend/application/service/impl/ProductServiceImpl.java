package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.ProductMapperDto;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    VendingMachineRepository productRepository;
    ProductMapperDto productMapper;

    @Override
    public List<ProductDTO> listProducts() {
        return productRepository.findAllProducts().stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID productId) {
        return productRepository.findProductById(new ProductId(productId)).map(productMapper::toDTO);
    }
}
