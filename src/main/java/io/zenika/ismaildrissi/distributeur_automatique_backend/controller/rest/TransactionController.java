package io.zenika.ismaildrissi.distributeur_automatique_backend.controller.rest;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.TransactionService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import org.hibernate.query.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing transaction operations in the vending machine system.
 *
 * <p>This controller provides HTTP endpoints for the complete transaction lifecycle
 * including money insertion, product selection, order completion, and cancellation.
 * It serves as the primary interface for client applications to interact with
 * the vending machine's transaction capabilities.</p>
 *
 * <p>The controller handles all transaction-related HTTP requests and delegates
 * business logic to the TransactionService. It provides endpoints for:
 * <ul>
 *   <li>Inserting money into the transaction</li>
 *   <li>Selecting and deselecting products</li>
 *   <li>Completing orders with change calculation</li>
 *   <li>Cancelling transactions with money return</li>
 *   <li>Querying transaction state</li>
 * </ul></p>
 *
 * <p>All endpoints are configured to handle CORS requests from the frontend
 * application and return JSON responses suitable for web clients.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    /** Service for handling transaction-related business logic */
    private final TransactionService transactionService;

    /**
     * Constructs a new TransactionController with the specified TransactionService.
     *
     * @param transactionService the service for handling transaction operations
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieves the total amount of money inserted in the current transaction.
     *
     * @return a MoneyDTO representing the total inserted amount
     */
    @GetMapping("/money/inserted")
    public MoneyDTO insertedMoney() {
        return transactionService.totalInsertedAmount();
    }

    /**
     * Retrieves the list of products currently selected in the transaction.
     *
     * @return a list of SelectedProductDTO representing the selected products
     */
    @GetMapping("/products/selected")
    public List<SelectedProductDTO> getSelectedProducts() {
        return transactionService.selectedProducts();
    }

    /**
     * Selects a product for purchase in the current transaction.
     *
     * @param productId the string representation of the product UUID to select
     */
    @GetMapping("/products/select/{productId}")
    public void selectProduct(@PathVariable String productId) {
        transactionService.selectProduct(UUID.fromString(productId));
    }

    /**
     * Removes a product from the current transaction.
     *
     * @param productId the string representation of the product UUID to unselect
     */
    @GetMapping("/products/unselect/{productId}")
    public void unselectProduct(@PathVariable String productId) {
        transactionService.unselectProduct(UUID.fromString(productId));
    }

    /**
     * Inserts money into the current transaction.
     *
     * @param moneyDTO the money denomination to insert
     */
    @PostMapping("/money")
    public void insertMoney(@RequestBody MoneyDTO moneyDTO) {
        transactionService.insertMoney(moneyDTO);
    }

    /**
     * Completes the current transaction, dispensing products and calculating change.
     *
     * @return an OrderDTO containing the dispensed products and returned change
     */
    @PostMapping("/complete")
    public OrderDTO complete() {
        return transactionService.completeOrder();
    }

    /**
     * Cancels the current transaction and returns all inserted money.
     *
     * @return an OrderDTO containing the returned money
     */
    @PostMapping("/cancel")
    public OrderDTO cancel() {
        return transactionService.cancelOrder();
    }
}
