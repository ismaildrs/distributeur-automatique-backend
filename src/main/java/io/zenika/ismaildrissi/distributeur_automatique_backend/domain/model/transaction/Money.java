package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InvalidMoneyValueException;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents a monetary value in the vending machine system using Moroccan Dirham (MAD).
 * This record enforces validation to ensure only valid coin denominations are accepted.
 *
 * <p>Valid coin denominations are: 0.5, 1.0, 2.0, 5.0, and 10.0 MAD.</p>
 *
 * <p>This is an immutable value object that guarantees the integrity of monetary values
 * throughout the system by validating input at construction time.</p>
 *
 * @param value the monetary value in MAD, must be positive and a valid denomination
 *
 * @throws InvalidMoneyValueException if the value is not positive or not a valid denomination
 *
 * @author Ismail Drissi
 * @since 1.0
 */
public record Money(double value) {

    /** Set of valid coin denominations in MAD */
    private static final Set<Double> VALID_VALUES = Set.of(0.5, 1.0, 2.0, 5.0, 10.0);

    /**
     * Compact constructor that validates the monetary value.
     *
     * @throws InvalidMoneyValueException if value is not positive or not a valid denomination
     */
    public Money {
        if (value <= 0) {
            throw new InvalidMoneyValueException("Money must be positive.");
        } else if (!VALID_VALUES.contains(value)) {
            throw new InvalidMoneyValueException("Invalid MAD coin: " + value);
        }
    }

    /**
     * Factory method to create a Money instance.
     *
     * @param value the monetary value in MAD
     * @return a new Money instance
     * @throws InvalidMoneyValueException if the value is invalid
     */
    public static Money of (double value) {
        return new Money(value);
    }
}