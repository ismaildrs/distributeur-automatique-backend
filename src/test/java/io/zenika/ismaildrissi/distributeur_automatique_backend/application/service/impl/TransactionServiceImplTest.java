package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.MoneyNotYetInserted;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.MoneyMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.SelectedProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.TransactionResult;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InsufficientFundsException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.VendingMachine;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private VendingMachine vendingMachine;

    @Mock
    private VendingMachineRepository vendingMachineRepository;

    @Mock
    private MoneyMapper moneyMapper;

    @Mock
    private SelectedProductMapper selectedProductMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private UUID productUuid;
    private ProductId productId;
    private Product product;
    private MoneyDTO moneyDTO;
    private Money money;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();
        productId = new ProductId(productUuid);
        product = new Product("Test Product", 2.0, productId, 5);
        moneyDTO = new MoneyDTO(5.0);
        money = new Money(5.0);
    }

    @Test
    void shouldInsertMoneySuccessfully() {
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);
        
        transactionService.insertMoney(moneyDTO);
        
        verify(moneyMapper).toDomain(moneyDTO);
    }

    @Test
    void shouldSelectProductSuccessfully() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        transactionService.insertMoney(moneyDTO);
        transactionService.selectProduct(productUuid);

        verify(vendingMachine).isProductAvailable(productId);
        verify(vendingMachine).getProduct(productId);
    }

    @Test
    void shouldThrowExceptionWhenSelectingUnavailableProduct() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(false);
        
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> transactionService.selectProduct(productUuid)
        );
        
        assertEquals("Product was not found", exception.getMessage());
        verify(vendingMachine).isProductAvailable(productId);
        verify(vendingMachine, never()).getProduct(productId);
    }

    @Test
    void shouldUnselectProductSuccessfully() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        transactionService.insertMoney(moneyDTO);
        transactionService.selectProduct(productUuid);
        transactionService.unselectProduct(productUuid);

        verify(vendingMachine, times(2)).isProductAvailable(productId);
        verify(vendingMachine, times(2)).getProduct(productId);
    }

    @Test
    void shouldThrowExceptionWhenUnselectingUnavailableProduct() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(false);
        
        ProductNotFoundException exception = assertThrows(
            ProductNotFoundException.class,
            () -> transactionService.unselectProduct(productUuid)
        );
        
        assertEquals("Product was not found", exception.getMessage());
        verify(vendingMachine).isProductAvailable(productId);
        verify(vendingMachine, never()).getProduct(productId);
    }

    @Test
    void shouldReturnTotalInsertedAmount() {
        MoneyDTO result = transactionService.totalInsertedAmount();
        
        assertNotNull(result);
        assertEquals(0.0, result.getValue());
    }

    @Test
    void shouldReturnSelectedProducts() {
        List<SelectedProductDTO> result = transactionService.selectedProducts();
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnSelectedProductsFromGetMethod() {
        List<SelectedProductDTO> result = transactionService.getSelectedProducst();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldCompleteOrderSuccessfully() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        Money changeAmount = new Money(2.0);
        List<Money> change = Arrays.asList(changeAmount);
        when(vendingMachine.calculateChange(3.0)).thenReturn(change);

        MoneyDTO changeDTOResult = new MoneyDTO(2.0);
        when(moneyMapper.toDTO(changeAmount)).thenReturn(changeDTOResult);

        SelectedProductDTO selectedProductDTO = new SelectedProductDTO(productUuid, "Test Product", 2.0);
        when(selectedProductMapper.toDto(any(SelectedProduct.class))).thenReturn(selectedProductDTO);

        transactionService.insertMoney(moneyDTO);
        transactionService.selectProduct(productUuid);

        OrderDTO result = transactionService.completeOrder();

        assertNotNull(result);
        assertEquals(1, result.getSelectedProducts().size());
        assertEquals(selectedProductDTO, result.getSelectedProducts().get(0));
        assertEquals(1, result.getReturnedMoney().size());
        assertEquals(changeDTOResult, result.getReturnedMoney().get(0));

        verify(vendingMachine).dispenseProduct(productId);
        verify(vendingMachine).calculateChange(3.0);
        verify(vendingMachineRepository).updateProduct(product);
    }

    @Test
    void shouldThrowExceptionWhenCompletingOrderWithoutTransaction() {
        MoneyNotYetInserted exception = assertThrows(
            MoneyNotYetInserted.class,
            () -> transactionService.completeOrder()
        );
        assertEquals("Can't complete order because there is no current transaction", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCompletingOrderWithInsufficientFunds() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(any(MoneyDTO.class))).thenReturn(new Money(1.0));

        transactionService.insertMoney(new MoneyDTO(1.0));

        InsufficientFundsException exception = assertThrows(
            InsufficientFundsException.class,
            () -> transactionService.selectProduct(productUuid)
        );
        assertEquals("Cannot add product due to insufficient funds.", exception.getMessage());
    }

    @Test
    void shouldCancelOrderSuccessfully() {
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        MoneyDTO returnedMoneyDTO = new MoneyDTO(5.0);
        when(moneyMapper.toDTO(money)).thenReturn(returnedMoneyDTO);

        transactionService.insertMoney(moneyDTO);

        OrderDTO result = transactionService.cancelOrder();

        assertNotNull(result);
        assertTrue(result.getSelectedProducts().isEmpty());
        assertEquals(1, result.getReturnedMoney().size());
        assertEquals(returnedMoneyDTO, result.getReturnedMoney().get(0));

        verify(moneyMapper).toDTO(money);
    }

    @Test
    void shouldCancelOrderWithSelectedProducts() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        MoneyDTO returnedMoneyDTO = new MoneyDTO(5.0);
        when(moneyMapper.toDTO(money)).thenReturn(returnedMoneyDTO);

        transactionService.insertMoney(moneyDTO);
        transactionService.selectProduct(productUuid);

        OrderDTO result = transactionService.cancelOrder();

        assertNotNull(result);
        assertTrue(result.getSelectedProducts().isEmpty());
        assertEquals(1, result.getReturnedMoney().size());
        assertEquals(returnedMoneyDTO, result.getReturnedMoney().get(0));

        verify(moneyMapper).toDTO(money);
        verifyNoInteractions(vendingMachineRepository);
    }

    @Test
    void shouldThrowExceptionWhenCancellingOrderWithoutTransaction() {
        MoneyNotYetInserted exception = assertThrows(
            MoneyNotYetInserted.class,
            () -> transactionService.cancelOrder()
        );
        assertEquals("Can't cancel order because there is no current transaction", exception.getMessage());
    }

    @Test
    void shouldCancelOrderWithMultipleMoneyInsertions() {
        MoneyDTO money1DTO = new MoneyDTO(2.0);
        MoneyDTO money2DTO = new MoneyDTO(1.0);
        Money money1 = new Money(2.0);
        Money money2 = new Money(1.0);

        when(moneyMapper.toDomain(money1DTO)).thenReturn(money1);
        when(moneyMapper.toDomain(money2DTO)).thenReturn(money2);

        MoneyDTO returnedMoney1DTO = new MoneyDTO(2.0);
        MoneyDTO returnedMoney2DTO = new MoneyDTO(1.0);
        when(moneyMapper.toDTO(money1)).thenReturn(returnedMoney1DTO);
        when(moneyMapper.toDTO(money2)).thenReturn(returnedMoney2DTO);

        transactionService.insertMoney(money1DTO);
        transactionService.insertMoney(money2DTO);

        OrderDTO result = transactionService.cancelOrder();

        assertNotNull(result);
        assertTrue(result.getSelectedProducts().isEmpty());
        assertEquals(2, result.getReturnedMoney().size());
        assertTrue(result.getReturnedMoney().contains(returnedMoney1DTO));
        assertTrue(result.getReturnedMoney().contains(returnedMoney2DTO));
    }

    @Test
    void shouldCompleteOrderWithExactChange() {
        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(moneyMapper.toDomain(moneyDTO)).thenReturn(money);

        when(vendingMachine.calculateChange(3.0)).thenReturn(Collections.emptyList());

        SelectedProductDTO selectedProductDTO = new SelectedProductDTO(productUuid, "Test Product", 2.0);
        when(selectedProductMapper.toDto(any(SelectedProduct.class))).thenReturn(selectedProductDTO);

        transactionService.insertMoney(moneyDTO);
        transactionService.selectProduct(productUuid);

        OrderDTO result = transactionService.completeOrder();

        assertNotNull(result);
        assertEquals(1, result.getSelectedProducts().size());
        assertTrue(result.getReturnedMoney().isEmpty());

        verify(vendingMachine).dispenseProduct(productId);
        verify(vendingMachine).calculateChange(3.0);
        verify(vendingMachineRepository).updateProduct(product);
    }

    @Test
    void shouldCompleteOrderWithMultipleProducts() {
        UUID product2Uuid = UUID.randomUUID();
        ProductId product2Id = new ProductId(product2Uuid);
        Product product2 = new Product("Test Product 2", 1.5, product2Id, 3);

        when(vendingMachine.isProductAvailable(productId)).thenReturn(true);
        when(vendingMachine.isProductAvailable(product2Id)).thenReturn(true);
        when(vendingMachine.getProduct(productId)).thenReturn(product);
        when(vendingMachine.getProduct(product2Id)).thenReturn(product2);

        MoneyDTO largeMoneyDTO = new MoneyDTO(10.0);
        Money largeMoney = new Money(10.0);
        when(moneyMapper.toDomain(largeMoneyDTO)).thenReturn(largeMoney);

        Money changeAmount = new Money(5.0);
        List<Money> change = Arrays.asList(changeAmount, new Money(1.0));
        when(vendingMachine.calculateChange(6.5)).thenReturn(change);

        MoneyDTO changeDTOResult1 = new MoneyDTO(5.0);
        MoneyDTO changeDTOResult2 = new MoneyDTO(1.0);
        when(moneyMapper.toDTO(changeAmount)).thenReturn(changeDTOResult1);
        when(moneyMapper.toDTO(new Money(1.0))).thenReturn(changeDTOResult2);

        SelectedProductDTO selectedProductDTO1 = new SelectedProductDTO(productUuid, "Test Product", 2.0);
        SelectedProductDTO selectedProductDTO2 = new SelectedProductDTO(product2Uuid, "Test Product 2", 1.5);
        when(selectedProductMapper.toDto(any(SelectedProduct.class)))
            .thenReturn(selectedProductDTO1)
            .thenReturn(selectedProductDTO2);

        transactionService.insertMoney(largeMoneyDTO);
        transactionService.selectProduct(productUuid);
        transactionService.selectProduct(product2Uuid);

        OrderDTO result = transactionService.completeOrder();

        assertNotNull(result);
        assertEquals(2, result.getSelectedProducts().size());
        assertEquals(2, result.getReturnedMoney().size());

        verify(vendingMachine).dispenseProduct(productId);
        verify(vendingMachine).dispenseProduct(product2Id);
        verify(vendingMachine).calculateChange(6.5);
        verify(vendingMachineRepository).updateProduct(product);
        verify(vendingMachineRepository).updateProduct(product2);
    }
}
