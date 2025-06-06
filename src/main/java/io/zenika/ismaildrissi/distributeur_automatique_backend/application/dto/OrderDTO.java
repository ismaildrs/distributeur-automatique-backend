package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    List<SelectedProductDTO> selectedProducts;
    List<MoneyDTO> returnedMoney;
}
