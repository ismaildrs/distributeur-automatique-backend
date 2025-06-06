package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.MoneyNotYetInserted;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.MoneyMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.SelectedProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.TransactionService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.TransactionResult;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.VendingMachine;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the TransactionService interface providing transaction management operations.
 *
 * <p>This service implementation orchestrates the complete transaction lifecycle in the vending
 * machine system. It manages the interaction between the domain layer (Transaction, VendingMachine)
 * and provides a simplified interface for the presentation layer through DTOs.</p>
 *
 * <p>The service maintains transaction state throughout the user session and handles:
 * <ul>
 *   <li>Transaction creation and lifecycle management</li>
 *   <li>Money insertion and tracking</li>
 *   <li>Product selection and validation</li>
 *   <li>Order completion with product dispensing and change calculation</li>
 *   <li>Order cancellation with money return</li>
 *   <li>Data transformation between domain objects and DTOs</li>
 * </ul></p>
 *
 * <p>The service is transactional to ensure data consistency across all operations
 * and uses dependency injection for all required components.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    /** Mapper for converting between Money domain objects and MoneyDTOs */
    private final MoneyMapper moneyMapper;

    /** Mapper for converting between SelectedProduct domain objects and SelectedProductDTOs */
    private final SelectedProductMapper selectedProductMapper;

    /** The vending machine domain object for product and change operations */
    private final VendingMachine vendingMachine;

    /** Repository for persisting product updates */
    private final VendingMachineRepository vendingMachineRepository;

    /** The current active transaction, null if no transaction is in progress */
    private Transaction transaction;

    /**
     * Gets the current transaction, creating a new one if none exists.
     *
     * @return the current active transaction
     */
    private Transaction currentTransaction() {
        if (transaction == null) {
            transaction = new Transaction(UUID.randomUUID().toString());
        }
        return transaction;
    }

    /**
     * Creates a new transaction, replacing any existing one.
     *
     * @return the newly created transaction
     */
    private Transaction newTransaction(){
        transaction = new Transaction(UUID.randomUUID().toString());
        return transaction;
    }

    /**
     * Removes the current transaction, typically after completion or cancellation.
     */
    private void removeTransaction() {
        this.transaction = null;
    }

    /**
     * Inserts money into the current transaction.
     *
     * @param money the money denomination to insert
     */
    @Override
    public void insertMoney(MoneyDTO money) {
        currentTransaction().insertMoney(moneyMapper.toDomain(money));
    }

    /**
     * Selects a product for purchase in the current transaction.
     *
     * <p>This method validates that the product exists and is available before
     * adding it to the transaction. The transaction will enforce business rules
     * such as sufficient funds validation.</p>
     *
     * @param productId the unique identifier of the product to select
     * @throws ProductNotFoundException if the product is not found or not available
     */
    @Override
    public void selectProduct(UUID productId) {
        ProductId id = new ProductId(productId);
        if (!vendingMachine.isProductAvailable(id)) {
            throw new ProductNotFoundException("Product was not found");
        }
        currentTransaction().addProduct(vendingMachine.getProduct(id));
    }

    @Override
    public OrderDTO completeOrder() {
        if(transaction == null) {
            throw new MoneyNotYetInserted("Can't complete order because there is no current transaction");
        }
        TransactionResult result = currentTransaction().complete();

        // Dispense selected products
        currentTransaction().selectedProducts().forEach(
                selectedProduct -> vendingMachine.dispenseProduct(selectedProduct.productId())
        );

        // Calculate inserted total
        Double insertedMoney = result.insertedMoney()
                .stream()
                .map(Money::value)
                .reduce(0.0, Double::sum);

        // Calculate change
        List<Money> change = vendingMachine.calculateChange(insertedMoney - result.totalPrice());

        // Map to DTO
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setSelectedProducts(
                result.selectedProducts().stream().map(selectedProductMapper::toDto).collect(Collectors.toList())
        );


        result.updateProducts().forEach(
                vendingMachineRepository::updateProduct
        );

        orderDTO.setReturnedMoney(
                change.stream().map(moneyMapper::toDTO).collect(Collectors.toList())
        );
        removeTransaction();
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder() {
        if(transaction == null) {
            throw new MoneyNotYetInserted("Can't cancel order because there is no current transaction");
        }
        TransactionResult result = currentTransaction().cancel();

        OrderDTO orderDTO = new OrderDTO();


        orderDTO.setSelectedProducts(List.of());
        orderDTO.setReturnedMoney(
                result.insertedMoney().stream().map(moneyMapper::toDTO).collect(Collectors.toList())
        );

        removeTransaction();
        return orderDTO;
    }

    @Override
    public List<SelectedProductDTO> selectedProducts() {
        return currentTransaction()
                .selectedProducts()
                .stream()
                .map(selectedProductMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MoneyDTO totalInsertedAmount() {
        return new MoneyDTO(currentTransaction().insertedMoney().stream().map(Money::value).reduce(0.0, Double::sum));
    }

    @Override
    public List<SelectedProductDTO> getSelectedProducst(){
        return currentTransaction().selectedProducts().stream().map(selectedProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void unselectProduct(UUID productId) {
        ProductId id = new ProductId(productId);
        if (!vendingMachine.isProductAvailable(id)) {
            throw new ProductNotFoundException("Product was not found");
        }
        currentTransaction().removeProduct(vendingMachine.getProduct(id));
    }
}
