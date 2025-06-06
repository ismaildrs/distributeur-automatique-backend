package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.jpa;

import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
}