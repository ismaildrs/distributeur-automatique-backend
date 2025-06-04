package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import java.util.List;

public class Transaction {
    List<Money> insertedMoney;
    List<SelectedProduct> selectedProducts;
    Long totalAmount;
    Long totalCost;
}
