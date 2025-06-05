package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.TransactionResultDTO;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void insertMoney(MoneyDTO money);
    TransactionResultDTO completeOrder();
    void selectProduct(UUID productId);
    TransactionResultDTO cancelOrder();
    List<SelectedProductDTO> selectedProducts();
}
