package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.jpa;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
}