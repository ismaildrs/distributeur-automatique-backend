package io.zenika.ismaildrissi.distributeur_automatique_backend;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Transaction;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.IllegalTransactionStateException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InsufficientFundsException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
public class DistributeurAutomatiqueBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeurAutomatiqueBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            Transaction transaction = new Transaction("wo1121");
            List<SelectedProduct> selectedProducts = new ArrayList<>();
            Product product = new Product("Iphone", 20.0, new ProductId(UUID.randomUUID()));
            try {
                transaction.addProduct(product);
                transaction.complete();
            } catch (Exception e) {
            }
        };
    }

}
