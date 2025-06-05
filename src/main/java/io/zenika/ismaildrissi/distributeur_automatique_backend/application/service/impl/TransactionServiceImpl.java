package io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.SelectedProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.TransactionResultDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.MoneyMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.ProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.SelectedProductMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper.TransactionResultMapper;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.TransactionService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    ProductService productService;
    Transaction transaction;
    MoneyMapper moneyMapper;
    TransactionResultMapper transactionResultMapper;
    SelectedProductMapper selectedProductMapper;
    ProductMapper productMapper;

    private Transaction currentTransaction() {
        if(transaction == null) {
            transaction = new Transaction(UUID.randomUUID().toString());
        }
        return transaction;
    }

    @Override
    public void insertMoney(MoneyDTO money) {
        currentTransaction().insertMoney(moneyMapper.toDomain(money));
    }

    @Override
    public TransactionResultDTO completeOrder() {
        return transactionResultMapper.toDTO(currentTransaction().complete());
    }

    @Override
    public void selectProduct(UUID productId) {
        Optional<ProductDTO> product = productService.getProductById(productId);
        if(product.isEmpty()) {
            throw new ProductNotFoundException("Product was not found");
        }
        currentTransaction().addProduct(productMapper.toDomain(product.get()));
    }

    @Override
    public TransactionResultDTO cancelOrder() {
        return transactionResultMapper.toDTO(currentTransaction().cancel());
    }

    @Override
    public List<SelectedProductDTO> selectedProducts() {
        return currentTransaction().selectedProducts().stream().map(selectedProductMapper::toDto).collect(Collectors.toList());
    }
}
