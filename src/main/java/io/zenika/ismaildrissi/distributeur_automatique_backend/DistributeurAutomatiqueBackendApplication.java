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

/**
 * Main Spring Boot application class for the Vending Machine Backend system.
 *
 * <p>This class serves as the entry point for the vending machine application,
 * configuring the Spring Boot context and initializing the application components.
 * The application implements a complete vending machine system with transaction
 * management, product inventory, and change calculation capabilities.</p>
 *
 * <p>The system is built using Domain-Driven Design principles with clear
 * separation between domain, application, infrastructure, and presentation layers.
 * It provides REST APIs for client applications to interact with the vending
 * machine functionality.</p>
 *
 * <p>Key features of the system:
 * <ul>
 *   <li>Product inventory management</li>
 *   <li>Transaction lifecycle management</li>
 *   <li>Money insertion and change calculation</li>
 *   <li>Product selection and dispensing</li>
 *   <li>Order completion and cancellation</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@SpringBootApplication
public class DistributeurAutomatiqueBackendApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(DistributeurAutomatiqueBackendApplication.class, args);
    }
}
