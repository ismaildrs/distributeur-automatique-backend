package io.zenika.ismaildrissi.distributeur_automatique_backend.controller.rest;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private List<ProductDTO> productList;

    @BeforeEach
    void setUp() {
        productDTO1 = new ProductDTO(UUID.randomUUID(), "Coke", 1.5, 5);
        productDTO2 = new ProductDTO(UUID.randomUUID(), "Water", 1.0, 3);
        productList = Arrays.asList(productDTO1, productDTO2);
    }

    @Test
    void shouldReturnAllProducts() {
        when(productService.listProducts()).thenReturn(productList);
        
        List<ProductDTO> result = productController.getAllProducts();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(productList, result);
        
        verify(productService).listProducts();
    }

    @Test
    void shouldReturnEmptyListWhenNoProducts() {
        when(productService.listProducts()).thenReturn(Arrays.asList());
        
        List<ProductDTO> result = productController.getAllProducts();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(productService).listProducts();
    }

    @Test
    void shouldCallProductServiceOnce() {
        when(productService.listProducts()).thenReturn(productList);
        
        productController.getAllProducts();
        
        verify(productService, times(1)).listProducts();
    }
}
