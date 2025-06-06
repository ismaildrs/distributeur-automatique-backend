package io.zenika.ismaildrissi.distributeur_automatique_backend.config;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.ProductId;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.VendingMachine;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.repository.VendingMachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class VendingMachineConfig {

    private final VendingMachineRepository vendingMachineRepository;

    @Bean
    public VendingMachine vendingMachine() {
        seedInitialProducts();
        List<Product> initialProducts = vendingMachineRepository.findAllProducts();
        List<Money> initialChange = getInitialChange();

        return new VendingMachine(initialProducts, initialChange);
    }

    private List<Money> getInitialChange() {
        return List.of(
                new Money(0.5), new Money(0.5), new Money(0.5), new Money(0.5), new Money(0.5),
                new Money(1), new Money(1), new Money(1), new Money(1), new Money(1),
                new Money(2), new Money(2), new Money(2), new Money(2), new Money(2),
                new Money(5), new Money(5), new Money(5), new Money(5),
                new Money(10), new Money(10), new Money(10)
        );
    }

    private void seedInitialProducts() {
        if (vendingMachineRepository.findAllProducts().isEmpty()) {
            List<Product> products = List.of(
                    createProduct("Energy Drink", 20.0, 3),
                    createProduct("Water Bottle", 1.5, 10),
                    createProduct("Sandwich", 4.0, 5),
                    createProduct("Chocolate Bar", 2.0, 7),
                    createProduct("Chips", 3.0, 6),
                    createProduct("Soda Can", 2.5, 8),
                    createProduct("Juice Box", 2.2, 6),
                    createProduct("Gum Pack", 1.0, 15),
                    createProduct("Granola Bar", 2.8, 4),
                    createProduct("Cookies", 3.5, 5),
                    createProduct("Iced Tea", 2.7, 5),
                    createProduct("Protein Bar", 3.2, 3)
            );

            products.forEach(vendingMachineRepository::saveProduct);
        }
    }

    private Product createProduct(String name, double price, int quantity) {
        return new Product(name, price, new ProductId(UUID.randomUUID()), quantity);
    }
}
