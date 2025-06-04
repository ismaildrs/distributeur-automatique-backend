package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InvalidMoneyValue;

import java.math.BigDecimal;
import java.util.Set;

public record Money(double value) {
    private static final Set<Double> VALID_VALUES = Set.of(0.5, 1.0, 2.0, 5.0, 10.0);

    public Money {
        if (value <= 0) {
            throw new InvalidMoneyValue("Money must be positive.");
        } else if (!VALID_VALUES.contains(value)) {
            throw new InvalidMoneyValue("Invalid MAD coin: " + value);
        }
    }

    public static Money of (double value) {
        return new Money(value);
    }
}