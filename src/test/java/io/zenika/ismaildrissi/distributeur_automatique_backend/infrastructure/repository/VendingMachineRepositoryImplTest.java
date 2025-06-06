package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.entity.ProductEntity;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper.ProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.jpa.SpringDataProductRepository;
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
class VendingMachineRepositoryImplTest {

    @Mock
    private SpringDataProductRepository springDataProductRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private VendingMachineRepositoryImpl vendingMachineRepository;

    private UUID productUuid;
    private ProductId productId;
    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productId = new ProductId(productUuid);
        product = new Product("Test Product", 2.0, productId, 5);
        productEntity = new ProductEntity(productUuid, "Test Product", 2.0, 5);
    }

    @Test
    void shouldFindProductById() {
        when(springDataProductRepository.findById(productUuid)).thenReturn(Optional.of(productEntity));
        when(productMapper.toDomain(productEntity)).thenReturn(product);
        
        Optional<Product> result = vendingMachineRepository.findProductById(productId);
        
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        
        verify(springDataProductRepository).findById(productUuid);
        verify(productMapper).toDomain(productEntity);
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        when(springDataProductRepository.findById(productUuid)).thenReturn(Optional.empty());
        
        Optional<Product> result = vendingMachineRepository.findProductById(productId);
        
        assertFalse(result.isPresent());
        
        verify(springDataProductRepository).findById(productUuid);
        verifyNoInteractions(productMapper);
    }

    @Test
    void shouldFindAllProducts() {
        List<ProductEntity> entities = Arrays.asList(productEntity);
        List<Product> expectedProducts = Arrays.asList(product);
        
        when(springDataProductRepository.findAll()).thenReturn(entities);
        when(productMapper.toDomain(productEntity)).thenReturn(product);
        
        List<Product> result = vendingMachineRepository.findAllProducts();
        
        assertEquals(1, result.size());
        assertEquals(expectedProducts, result);
        
        verify(springDataProductRepository).findAll();
        verify(productMapper).toDomain(productEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        when(springDataProductRepository.findAll()).thenReturn(Arrays.asList());
        
        List<Product> result = vendingMachineRepository.findAllProducts();
        
        assertTrue(result.isEmpty());
        
        verify(springDataProductRepository).findAll();
        verifyNoInteractions(productMapper);
    }

    @Test
    void shouldSaveProduct() {
        when(productMapper.toEntity(product)).thenReturn(productEntity);
        when(springDataProductRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDomain(productEntity)).thenReturn(product);
        
        Product result = vendingMachineRepository.saveProduct(product);
        
        assertEquals(product, result);
        
        verify(productMapper).toEntity(product);
        verify(springDataProductRepository).save(productEntity);
        verify(productMapper).toDomain(productEntity);
    }

    @Test
    void shouldUpdateProduct() {
        when(productMapper.toEntity(product)).thenReturn(productEntity);
        when(springDataProductRepository.save(productEntity)).thenReturn(productEntity);
        when(productMapper.toDomain(productEntity)).thenReturn(product);
        
        Product result = vendingMachineRepository.updateProduct(product);
        
        assertEquals(product, result);
        
        verify(productMapper).toEntity(product);
        verify(springDataProductRepository).save(productEntity);
        verify(productMapper).toDomain(productEntity);
    }

    @Test
    void shouldDeleteProductById() {
        vendingMachineRepository.deleteProductById(productId);
        
        verify(springDataProductRepository).deleteById(productUuid);
    }

    @Test
    void shouldCheckIfProductExists() {
        when(springDataProductRepository.existsById(productUuid)).thenReturn(true);
        
        boolean result = vendingMachineRepository.productExistsById(productId);
        
        assertTrue(result);
        verify(springDataProductRepository).existsById(productUuid);
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExist() {
        when(springDataProductRepository.existsById(productUuid)).thenReturn(false);
        
        boolean result = vendingMachineRepository.productExistsById(productId);
        
        assertFalse(result);
        verify(springDataProductRepository).existsById(productUuid);
    }
}
