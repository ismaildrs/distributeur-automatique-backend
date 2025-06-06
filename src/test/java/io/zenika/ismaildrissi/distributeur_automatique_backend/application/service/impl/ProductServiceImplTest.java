package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.ProductMapperDto;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private VendingMachineRepository productRepository;

    @Mock
    private ProductMapperDto productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product1;
    private Product product2;
    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private UUID productUuid;
    private ProductId productId;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productId = new ProductId(productUuid);
        
        product1 = new Product("Coke", 1.5, productId, 5);
        product2 = new Product("Water", 1.0, new ProductId(UUID.randomUUID()), 3);
        
        productDTO1 = new ProductDTO(productUuid, "Coke", 1.5, 5);
        productDTO2 = new ProductDTO(UUID.randomUUID(), "Water", 1.0, 3);
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductDTO> expectedDTOs = Arrays.asList(productDTO1, productDTO2);
        
        when(productRepository.findAllProducts()).thenReturn(products);
        when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        when(productMapper.toDTO(product2)).thenReturn(productDTO2);
        
        List<ProductDTO> result = productService.listProducts();
        
        assertEquals(2, result.size());
        assertEquals(expectedDTOs, result);
        
        verify(productRepository).findAllProducts();
        verify(productMapper).toDTO(product1);
        verify(productMapper).toDTO(product2);
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        when(productRepository.findAllProducts()).thenReturn(Arrays.asList());
        
        List<ProductDTO> result = productService.listProducts();
        
        assertTrue(result.isEmpty());
        verify(productRepository).findAllProducts();
        verifyNoInteractions(productMapper);
    }

    @Test
    void shouldReturnProductById() {
        when(productRepository.findProductById(productId)).thenReturn(Optional.of(product1));
        when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        
        Optional<ProductDTO> result = productService.getProductById(productUuid);
        
        assertTrue(result.isPresent());
        assertEquals(productDTO1, result.get());
        
        verify(productRepository).findProductById(productId);
        verify(productMapper).toDTO(product1);
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        when(productRepository.findProductById(productId)).thenReturn(Optional.empty());
        
        Optional<ProductDTO> result = productService.getProductById(productUuid);
        
        assertFalse(result.isPresent());
        
        verify(productRepository).findProductById(productId);
        verifyNoInteractions(productMapper);
    }
}
