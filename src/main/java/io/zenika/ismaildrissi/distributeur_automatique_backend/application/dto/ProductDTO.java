package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    UUID id;
    String name;
    Double price;
    Integer quantity;
}
