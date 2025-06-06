package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.exceptions.ProductNotFoundException;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;

import java.util.*;

/**
 * Represents a vending machine that manages products and change dispensing.
 *
 * <p>The VendingMachine is the core domain entity that encapsulates the business logic
 * for product management, change calculation, and product dispensing. It maintains
 * an inventory of products and available change denominations.</p>
 *
 * <p>Key responsibilities:
 * <ul>
 *   <li>Managing product inventory and availability</li>
 *   <li>Calculating and dispensing change using a greedy algorithm</li>
 *   <li>Validating product availability before dispensing</li>
 *   <li>Maintaining change inventory for accurate change dispensing</li>
 * </ul></p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public class VendingMachine {

    /** Map of available change denominations and their quantities */
    private final Map<Money, Integer> availableChange;

    /** Map of products indexed by their unique identifiers */
    private final Map<ProductId, Product> products;

    /**
     * Constructs a new VendingMachine with initial products and change.
     *
     * @param initialProducts the list of products to stock in the machine
     * @param initialChange the list of money denominations available for change
     */
    public VendingMachine(List<Product> initialProducts, List<Money> initialChange) {
        this.products = new HashMap<>();
        for (Product p : initialProducts) {
            products.put(p.productId(), p);
        }

        this.availableChange = new HashMap<>();
        for (Money m : initialChange) {
            availableChange.put(m, availableChange.getOrDefault(m, 0) + 1);
        }
    }

    /**
     * Checks if the machine can dispense the exact change for the given amount.
     *
     * @param amount the change amount to check
     * @return true if exact change can be dispensed, false otherwise
     */
    public boolean canDispenseChange(double amount) {
        return !calculateChange(amount).isEmpty();
    }

    /**
     * Retrieves a product by its identifier.
     *
     * @param productId the unique identifier of the product
     * @return the product with the specified ID
     * @throws ProductNotFoundException if the product is not found
     */
    public Product getProduct(ProductId productId) {
        if(!products.containsKey(productId)) {
            throw new ProductNotFoundException("Product was not found");
        }
        return products.get(productId);
    }


    // Greedy based change calculation
    public List<Money> calculateChange(double amount) {
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

        if (remaining > 0) return Collections.emptyList();
        return change;
    }

    /**
     * Checks if a product is available for purchase.
     *
     * @param id the product identifier to check
     * @return true if the product exists and has quantity greater than 0
     */
    public boolean isProductAvailable(ProductId id) {
        return products.containsKey(id) && products.get(id).quantity() > 0;
    }

    /**
     * Dispenses a product by decreasing its quantity.
     *
     * @param id the identifier of the product to dispense
     * @throws ProductNotFoundException if the product is not available
     */
    public void dispenseProduct(ProductId id) {
        if (!isProductAvailable(id)) {
            throw new ProductNotFoundException("Product not available");
        }
        products.get(id).decreaseQuantity(); // You’ll need a method for this
    }

    /**
     * Returns a copy of all products in the vending machine.
     *
     * @return an immutable list of all products
     */
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }
}
