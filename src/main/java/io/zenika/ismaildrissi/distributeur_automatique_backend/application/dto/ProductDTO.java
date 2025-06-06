package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object representing a product in the vending machine system.
 *
 * <p>This DTO is used to transfer product information between the application
 * layer and the presentation layer. It contains all the essential information
 * about a product that the client needs to display and interact with.</p>
 *
 * <p>The DTO is designed to be serializable for REST API responses and
 * provides a stable interface that abstracts the underlying domain model
 * complexity from the client.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    /** The unique identifier of the product */
    UUID id;

    /** The display name of the product */
    String name;

    /** The price of the product in MAD */
    Double price;

    /** The current quantity available in the vending machine */
    Integer quantity;
}
