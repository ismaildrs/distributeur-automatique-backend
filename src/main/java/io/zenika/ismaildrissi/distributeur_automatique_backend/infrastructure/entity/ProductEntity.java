package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.entity;


import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    private UUID id;
    private String name;
    private double price;
}
