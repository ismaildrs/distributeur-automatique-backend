package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;

import java.util.List;
import java.util.Set;

public record TransactionResult(
        List<SelectedProduct> selectedProducts,
        List<Money> insertedMoney,
        Double totalPrice,
        Set<Product> updateProducts
) {
}
