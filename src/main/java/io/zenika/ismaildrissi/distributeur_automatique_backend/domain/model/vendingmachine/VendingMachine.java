package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;

import java.util.*;

public class VendingMachine {
    private final Map<Money, Integer> availableChange;
    private final Map<ProductId, Product> products;

    public VendingMachine(List<Product> initialProducts, List<Money> initialChange) {
        this.products = new HashMap<>();
        for (Product p : initialProducts) {
            products.put(p.productId(), p);
        }

        this.availableChange = new HashMap<>(); // assuming Money is an enum
        for (Money m : initialChange) {
            availableChange.put(m, availableChange.getOrDefault(m, 0) + 1);
        }
    }

    public boolean canDispenseChange(double amount) {
        return !calculateChange(amount).isEmpty();
    }

    public Product getProduct(ProductId productId) {
        if(!products.containsKey(productId)) {
            throw new ProductNotFoundException("Product was not found");
        }
        return products.get(productId);
    }

    public List<Money> calculateChange(double amount) {
        // Greedy change-making algorithm

        List<Money> change = new ArrayList<>();
        double remaining = amount;

        List<Money> sortedMoney = availableChange.keySet().stream()
                .sorted(Comparator.comparingDouble(Money::value).reversed())
                .toList();

        for (Money m : sortedMoney) {
            while (remaining >= m.value() && availableChange.get(m) > 0) {
                change.add(m);
                remaining -= m.value();
                remaining = Math.round(remaining * 100.0) / 100.0;
                availableChange.put(m, availableChange.get(m) - 1);
            }
        }

        if (remaining > 0) return Collections.emptyList(); // cannot give exact change
        return change;
    }

    public boolean isProductAvailable(ProductId id) {
        return products.containsKey(id) && products.get(id).quantity() > 0;
    }

    public void dispenseProduct(ProductId id) {
        if (!isProductAvailable(id)) {
            throw new ProductNotFoundException("Product not available");
        }
        products.get(id).decreaseQuantity(); // You’ll need a method for this
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }
}
