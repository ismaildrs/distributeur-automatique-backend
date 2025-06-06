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
                // 0.5 x 5
                new Money(0.5), new Money(0.5), new Money(0.5), new Money(0.5), new Money(0.5),

                // 1 x 5
                new Money(1), new Money(1), new Money(1), new Money(1), new Money(1),

                // 2 x 5
                new Money(2), new Money(2), new Money(2), new Money(2), new Money(2),

                // 5 x 4
                new Money(5), new Money(5), new Money(5), new Money(5),

                // 10 x 3
                new Money(10), new Money(10), new Money(10)
        );
    }


        private void seedInitialProducts() {
        if (vendingMachineRepository.findAllProducts().isEmpty()) {
            vendingMachineRepository.saveProduct(new Product(
                    "Energy Drink",
                    20.0,
                    new ProductId(UUID.randomUUID()),
                    3
            ));

            vendingMachineRepository.saveProduct(new Product(
                    "Water Bottle",
                    1.5,
                    new ProductId(UUID.randomUUID()),
                    10
            ));

            vendingMachineRepository.saveProduct(new Product(
                    "Sandwich",
                    4.0,
                    new ProductId(UUID.randomUUID()),
                    5
            ));
        }
    }
}
