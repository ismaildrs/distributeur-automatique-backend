package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.exceptions.InvalidMoneyValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateValidMoney() {
        Money money = new Money(1.0);
        assertEquals(1.0, money.value());
    }

    @Test
    void shouldCreateValidMoneyWithAllValidValues() {
        assertDoesNotThrow(() -> new Money(0.5));
        assertDoesNotThrow(() -> new Money(1.0));
        assertDoesNotThrow(() -> new Money(2.0));
        assertDoesNotThrow(() -> new Money(5.0));
        assertDoesNotThrow(() -> new Money(10.0));
    }

    @Test
    void shouldThrowExceptionForNegativeValue() {
        InvalidMoneyValueException exception = assertThrows(
            InvalidMoneyValueException.class,
            () -> new Money(-1.0)
        );
        assertEquals("Money must be positive.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForZeroValue() {
        InvalidMoneyValueException exception = assertThrows(
            InvalidMoneyValueException.class,
            () -> new Money(0.0)
        );
        assertEquals("Money must be positive.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidValue() {
        InvalidMoneyValueException exception = assertThrows(
            InvalidMoneyValueException.class,
            () -> new Money(3.0)
        );
        assertEquals("Invalid MAD coin: 3.0", exception.getMessage());
    }

    @Test
    void shouldCreateMoneyUsingOfMethod() {
        Money money = Money.of(5.0);
        assertEquals(5.0, money.value());
    }

    @Test
    void shouldThrowExceptionForInvalidValueUsingOfMethod() {
        InvalidMoneyValueException exception = assertThrows(
            InvalidMoneyValueException.class,
            () -> Money.of(7.5)
        );
        assertEquals("Invalid MAD coin: 7.5", exception.getMessage());
    }
}
