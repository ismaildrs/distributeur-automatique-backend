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

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final MoneyMapper moneyMapper;
    private final SelectedProductMapper selectedProductMapper;
    private final VendingMachine vendingMachine;
    private final VendingMachineRepository vendingMachineRepository;

    private Transaction transaction;

    private Transaction currentTransaction() {
        if (transaction == null) {
            transaction = new Transaction(UUID.randomUUID().toString());
        }
        return transaction;
    }

    private Transaction newTransaction(){
        transaction = new Transaction(UUID.randomUUID().toString());
        return transaction;
    }

    private void removeTransaction() {
        this.transaction = null;
    }

    @Override
    public void insertMoney(MoneyDTO money) {
        currentTransaction().insertMoney(moneyMapper.toDomain(money));
    }

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
