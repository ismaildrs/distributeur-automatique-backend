package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.OrderDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import org.hibernate.query.Order;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void insertMoney(MoneyDTO money);
    OrderDTO completeOrder();
    MoneyDTO totalInsertedAmount();
    void selectProduct(UUID productId);
    void unselectProduct(UUID productId);
    OrderDTO cancelOrder();
    List<SelectedProductDTO> selectedProducts();

    List<SelectedProductDTO> getSelectedProducst();
}
