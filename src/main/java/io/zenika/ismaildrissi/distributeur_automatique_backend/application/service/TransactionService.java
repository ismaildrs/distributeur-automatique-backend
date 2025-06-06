package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import org.hibernate.query.Order;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing transactions in the vending machine system.
 *
 * <p>This service orchestrates the complete transaction lifecycle including
 * money insertion, product selection, order completion, and cancellation.
 * It maintains transaction state and enforces business rules throughout
 * the purchase process.</p>
 *
 * <p>The service handles the coordination between the domain layer (Transaction,
 * VendingMachine) and provides a simplified interface for the presentation layer.
 * All monetary amounts and product information are exchanged using DTOs.</p>
 *
 * <p>Key operations supported:
 * <ul>
 *   <li>Money insertion and tracking</li>
 *   <li>Product selection and deselection</li>
 *   <li>Order completion with change calculation</li>
 *   <li>Order cancellation with money return</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public interface TransactionService {

    /**
     * Inserts money into the current transaction.
     *
     * @param money the money denomination to insert
     */
    void insertMoney(MoneyDTO money);

    /**
     * Completes the current transaction, dispensing products and calculating change.
     *
     * @return an OrderDTO containing the dispensed products and change
     */
    OrderDTO completeOrder();

    /**
     * Returns the total amount of money inserted in the current transaction.
     *
     * @return the total inserted amount as a MoneyDTO
     */
    MoneyDTO totalInsertedAmount();

    /**
     * Selects a product for purchase in the current transaction.
     *
     * @param productId the unique identifier of the product to select
     */
    void selectProduct(UUID productId);

    /**
     * Removes a product from the current transaction.
     *
     * @param productId the unique identifier of the product to unselect
     */
    void unselectProduct(UUID productId);

    /**
     * Cancels the current transaction and returns all inserted money.
     *
     * @return an OrderDTO containing the returned money
     */
    OrderDTO cancelOrder();

    /**
     * Returns the list of currently selected products.
     *
     * @return a list of selected products as DTOs
     */
    List<SelectedProductDTO> selectedProducts();

    /**
     * Returns the list of currently selected products.
     *
     * @return a list of selected products as DTOs
     */
    List<SelectedProductDTO> getSelectedProducst();
}
