package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductEntity {
    @Id
    private UUID id;
    private String name;
    private double price;
    private int quantity;
}
