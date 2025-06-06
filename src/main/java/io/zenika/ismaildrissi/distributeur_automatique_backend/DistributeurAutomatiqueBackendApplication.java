package io.zenika.ismaildrissi.distributeur_automatique_backend;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@SpringBootApplication
public class DistributeurAutomatiqueBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeurAutomatiqueBackendApplication.class, args);
    }
}
