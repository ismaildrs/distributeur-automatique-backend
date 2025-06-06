package io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object representing a monetary value in the vending machine system.
 *
 * <p>This DTO encapsulates monetary amounts in Moroccan Dirham (MAD) for transfer
 * between application layers. It provides a simple representation of money that
 * can be easily serialized for API responses and deserialized from client requests.</p>
 *
 * <p>The DTO abstracts the domain-level validation and business rules of the Money
 * domain object, allowing for flexible data transfer while maintaining type safety.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class MoneyDTO {

    /** The monetary value in MAD */
    double value;
}
