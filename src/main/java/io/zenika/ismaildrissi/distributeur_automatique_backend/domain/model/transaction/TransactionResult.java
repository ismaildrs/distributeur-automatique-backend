package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import java.util.List;

public record TransactionResult(
        List<SelectedProduct> selectedProducts,
        List<Money> returnedMoney
) {
}
