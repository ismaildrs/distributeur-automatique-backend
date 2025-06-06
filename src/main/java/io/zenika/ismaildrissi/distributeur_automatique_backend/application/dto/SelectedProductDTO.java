package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object representing a product that has been selected for purchase.
 *
 * <p>This DTO captures the essential information about a product at the time of
 * selection, including the price at that moment. This ensures that the client
 * receives consistent pricing information even if the product's current price
 * changes during the transaction.</p>
 *
 * <p>Used primarily in transaction contexts to show what products have been
 * selected and their prices at selection time.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedProductDTO {

    /** The unique identifier of the selected product */
    UUID id;

    /** The name of the product at selection time */
    String name;

    /** The price of the product at selection time */
    Double price;
}
