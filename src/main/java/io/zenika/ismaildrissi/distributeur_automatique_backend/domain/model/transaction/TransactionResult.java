package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.vendingmachine.Product;

import java.util.List;
import java.util.Set;

/**
 * Represents the result of a completed or cancelled transaction.
 *
 * <p>This immutable record encapsulates all the information about what happened
 * during a transaction, including the products that were selected, the money
 * that was inserted, the total price, and which products need to be updated
 * in the repository.</p>
 *
 * <p>The TransactionResult serves as a data transfer object between the domain
 * layer and the application layer, providing all necessary information for
 * the application services to complete the transaction processing.</p>
 *
 * @param selectedProducts the list of products that were selected during the transaction
 * @param insertedMoney the list of money denominations that were inserted
 * @param totalPrice the total price of all selected products
 * @param updateProducts the set of products that need to be updated in the repository
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public record TransactionResult(
        List<SelectedProduct> selectedProducts,
        List<Money> insertedMoney,
        Double totalPrice,
        Set<Product> updateProducts
) {
}
