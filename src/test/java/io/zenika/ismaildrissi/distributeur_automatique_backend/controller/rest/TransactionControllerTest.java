package io.zenika.ismaildrissi.distributeur_automatique_backend.controller.rest;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.TransactionService;
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
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private MoneyDTO moneyDTO;
    private OrderDTO orderDTO;
    private SelectedProductDTO selectedProductDTO;
    private UUID productUuid;

    @BeforeEach
    void setUp() {
        moneyDTO = new MoneyDTO(5.0);
        orderDTO = new OrderDTO();
        productUuid = UUID.randomUUID();
        selectedProductDTO = new SelectedProductDTO(productUuid, "Test Product", 2.0);
    }

    @Test
    void shouldReturnInsertedMoney() {
        when(transactionService.totalInsertedAmount()).thenReturn(moneyDTO);
        
        MoneyDTO result = transactionController.insertedMoney();
        
        assertNotNull(result);
        assertEquals(moneyDTO, result);
        verify(transactionService).totalInsertedAmount();
    }

    @Test
    void shouldReturnSelectedProducts() {
        List<SelectedProductDTO> selectedProducts = Arrays.asList(selectedProductDTO);
        when(transactionService.selectedProducts()).thenReturn(selectedProducts);
        
        List<SelectedProductDTO> result = transactionController.getSelectedProducts();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(selectedProducts, result);
        verify(transactionService).selectedProducts();
    }

    @Test
    void shouldSelectProduct() {
        String productIdString = productUuid.toString();
        
        transactionController.selectProduct(productIdString);
        
        verify(transactionService).selectProduct(productUuid);
    }

    @Test
    void shouldUnselectProduct() {
        String productIdString = productUuid.toString();
        
        transactionController.unselectProduct(productIdString);
        
        verify(transactionService).unselectProduct(productUuid);
    }

    @Test
    void shouldInsertMoney() {
        transactionController.insertMoney(moneyDTO);
        
        verify(transactionService).insertMoney(moneyDTO);
    }

    @Test
    void shouldCompleteTransaction() {
        when(transactionService.completeOrder()).thenReturn(orderDTO);
        
        OrderDTO result = transactionController.complete();
        
        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(transactionService).completeOrder();
    }

    @Test
    void shouldCancelTransaction() {
        when(transactionService.cancelOrder()).thenReturn(orderDTO);
        
        OrderDTO result = transactionController.cancel();
        
        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(transactionService).cancelOrder();
    }

    @Test
    void shouldCallTransactionServiceMethodsOnce() {
        transactionController.insertMoney(moneyDTO);
        transactionController.selectProduct(productUuid.toString());
        transactionController.unselectProduct(productUuid.toString());
        
        verify(transactionService, times(1)).insertMoney(moneyDTO);
        verify(transactionService, times(1)).selectProduct(productUuid);
        verify(transactionService, times(1)).unselectProduct(productUuid);
    }
}
