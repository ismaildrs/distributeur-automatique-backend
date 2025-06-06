package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperDtoTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductMapperDto productMapperDto;

    private UUID productUuid;
    private ProductId productId;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productId = new ProductId(productUuid);
        product = new Product("Test Product", 2.5, productId, 5);
        productDTO = new ProductDTO(productUuid, "Test Product", 2.5, 5);
    }

    @Test
    void shouldMapProductToDTO() {
        ProductDTO result = productMapperDto.toDTO(product);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(2.5, result.getPrice());
        assertEquals(5, result.getQuantity());
    }

    @Test
    void shouldMapProductDTOToDomain() {
        when(modelMapper.map(productDTO, Product.class)).thenReturn(product);
        
        Product result = productMapperDto.toDomain(productDTO);
        
        assertNotNull(result);
        assertEquals(product, result);
        verify(modelMapper).map(productDTO, Product.class);
    }

    @Test
    void shouldPreserveAllFieldsWhenMappingToDTO() {
        ProductDTO result = productMapperDto.toDTO(product);
        
        assertEquals(product.productId().id(), result.getId());
        assertEquals(product.name(), result.getName());
        assertEquals(product.price(), result.getPrice());
        assertEquals(product.quantity(), result.getQuantity());
    }

    @Test
    void shouldHandleZeroQuantityProduct() {
        Product zeroQuantityProduct = new Product("Out of Stock", 1.0, productId, 0);
        
        ProductDTO result = productMapperDto.toDTO(zeroQuantityProduct);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Out of Stock", result.getName());
        assertEquals(1.0, result.getPrice());
        assertEquals(0, result.getQuantity());
    }

    @Test
    void shouldHandleHighPriceProduct() {
        Product expensiveProduct = new Product("Expensive Item", 99.99, productId, 1);
        
        ProductDTO result = productMapperDto.toDTO(expensiveProduct);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Expensive Item", result.getName());
        assertEquals(99.99, result.getPrice());
        assertEquals(1, result.getQuantity());
    }

    @Test
    void shouldHandleLowPriceProduct() {
        Product cheapProduct = new Product("Cheap Item", 0.5, productId, 10);
        
        ProductDTO result = productMapperDto.toDTO(cheapProduct);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Cheap Item", result.getName());
        assertEquals(0.5, result.getPrice());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void shouldHandleProductWithLongName() {
        Product longNameProduct = new Product("Very Long Product Name That Exceeds Normal Length", 5.0, productId, 3);
        
        ProductDTO result = productMapperDto.toDTO(longNameProduct);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Very Long Product Name That Exceeds Normal Length", result.getName());
        assertEquals(5.0, result.getPrice());
        assertEquals(3, result.getQuantity());
    }

    @Test
    void shouldCallModelMapperForDomainMapping() {
        ProductDTO customDTO = new ProductDTO(productUuid, "Custom Product", 3.0, 7);
        Product expectedProduct = new Product("Custom Product", 3.0, productId, 7);
        when(modelMapper.map(customDTO, Product.class)).thenReturn(expectedProduct);
        
        Product result = productMapperDto.toDomain(customDTO);
        
        assertNotNull(result);
        assertEquals(expectedProduct, result);
        verify(modelMapper, times(1)).map(customDTO, Product.class);
    }

    @Test
    void shouldMapMultipleProductsConsistently() {
        Product product1 = new Product("Product 1", 1.0, new ProductId(UUID.randomUUID()), 5);
        Product product2 = new Product("Product 2", 2.0, new ProductId(UUID.randomUUID()), 3);
        
        ProductDTO dto1 = productMapperDto.toDTO(product1);
        ProductDTO dto2 = productMapperDto.toDTO(product2);
        
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals("Product 1", dto1.getName());
        assertEquals("Product 2", dto2.getName());
        assertEquals(1.0, dto1.getPrice());
        assertEquals(2.0, dto2.getPrice());
        assertEquals(5, dto1.getQuantity());
        assertEquals(3, dto2.getQuantity());
    }
}
