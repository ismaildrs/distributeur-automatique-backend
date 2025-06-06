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

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/money/inserted")
    public MoneyDTO insertedMoney() {
        return transactionService.totalInsertedAmount();
    }

    @GetMapping("/products/selected")
    public List<SelectedProductDTO> getSelectedProducts() {
        return transactionService.selectedProducts();
    }

    @GetMapping("/products/select/{productId}")
    public void selectProduct(@PathVariable String productId) {
        transactionService.selectProduct(UUID.fromString(productId));
    }

    @GetMapping("/products/unselect/{productId}")
    public void unselectProduct(@PathVariable String productId) {
        transactionService.unselectProduct(UUID.fromString(productId));
    }

    @PostMapping("/money")
    public void insertMoney(@RequestBody MoneyDTO moneyDTO) {
        transactionService.insertMoney(moneyDTO);
    }

    @PostMapping("/complete")
    public OrderDTO complete() {
        return transactionService.completeOrder();
    }

    @PostMapping("/cancel")
    public OrderDTO cancel() {
        return transactionService.cancelOrder();
    }
}
