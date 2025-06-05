package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionResultDTO {
    List<ProductDTO> products;
    List<MoneyDTO> moneys;
}
