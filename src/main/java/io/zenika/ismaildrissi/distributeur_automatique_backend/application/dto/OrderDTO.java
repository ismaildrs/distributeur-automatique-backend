package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.SelectedProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object representing the result of a completed or cancelled order.
 *
 * <p>This DTO encapsulates the outcome of a transaction, containing information
 * about the products that were dispensed (if any) and the money that was returned
 * to the customer. It serves as the response object for both order completion
 * and cancellation operations.</p>
 *
 * <p>For completed orders, selectedProducts contains the purchased items and
 * returnedMoney contains the change. For cancelled orders, selectedProducts
 * is empty and returnedMoney contains all the inserted money.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    /** List of products that were selected and dispensed */
    List<SelectedProductDTO> selectedProducts;

    /** List of money denominations returned to the customer */
    List<MoneyDTO> returnedMoney;
}
