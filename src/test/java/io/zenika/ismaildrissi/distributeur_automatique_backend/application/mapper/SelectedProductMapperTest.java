package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
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

@ExtendWith(MockitoExtension.class)
class SelectedProductMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SelectedProductMapper selectedProductMapper;

    private UUID productUuid;
    private ProductId productId;
    private Product product;
    private SelectedProduct selectedProduct;
    private SelectedProductDTO selectedProductDTO;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productId = new ProductId(productUuid);
        product = new Product("Test Product", 2.5, productId, 5);
        selectedProduct = new SelectedProduct(product);
        selectedProductDTO = new SelectedProductDTO(productUuid, "Test Product", 2.5);
    }

    @Test
    void shouldMapSelectedProductDTOToDomain() {
        SelectedProduct result = selectedProductMapper.toDomain(selectedProductDTO);
        
        assertNotNull(result);
        assertEquals(productUuid, result.productId().id());
        assertEquals("Test Product", result.name());
        assertEquals(2.5, result.priceAtSelection());
    }

    @Test
    void shouldMapSelectedProductToDTO() {
        SelectedProductDTO result = selectedProductMapper.toDto(selectedProduct);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(2.5, result.getPrice());
    }

    @Test
    void shouldPreserveAllFieldsInBothDirections() {
        SelectedProduct domain = selectedProductMapper.toDomain(selectedProductDTO);
        SelectedProductDTO backToDto = selectedProductMapper.toDto(domain);
        
        assertEquals(selectedProductDTO.getId(), backToDto.getId());
        assertEquals(selectedProductDTO.getName(), backToDto.getName());
        assertEquals(selectedProductDTO.getPrice(), backToDto.getPrice());
    }

    @Test
    void shouldMapSelectedProductCreatedFromProduct() {
        SelectedProduct productBasedSelection = new SelectedProduct(product);
        
        SelectedProductDTO result = selectedProductMapper.toDto(productBasedSelection);
        
        assertNotNull(result);
        assertEquals(productUuid, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(2.5, result.getPrice());
    }

    @Test
    void shouldMapSelectedProductWithCustomParameters() {
        SelectedProductDTO customDTO = new SelectedProductDTO(productUuid, "Custom Product", 3.0);
        
        SelectedProduct result = selectedProductMapper.toDomain(customDTO);
        
        assertNotNull(result);
        assertEquals(productUuid, result.productId().id());
        assertEquals("Custom Product", result.name());
        assertEquals(3.0, result.priceAtSelection());
    }
}
