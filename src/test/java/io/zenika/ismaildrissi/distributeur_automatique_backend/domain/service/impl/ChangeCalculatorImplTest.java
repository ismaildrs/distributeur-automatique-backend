package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChangeCalculatorImplTest {

    private ChangeCalculatorImpl changeCalculator;

    @BeforeEach
    void setUp() {
        changeCalculator = new ChangeCalculatorImpl();
    }

    @Test
    void shouldCalculateChangeForExactAmount() {
        List<Money> change = changeCalculator.calculateChange(5.0);
        
        assertNotNull(change);
        assertEquals(1, change.size());
        assertEquals(5.0, change.get(0).value());
    }

    @Test
    void shouldCalculateChangeUsingGreedyAlgorithm() {
        List<Money> change = changeCalculator.calculateChange(7.5);
        
        assertNotNull(change);
        assertFalse(change.isEmpty());
        
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(7.5, totalChange, 0.01);
        
        long fiveCount = change.stream().filter(m -> m.value() == 5.0).count();
        long twoCount = change.stream().filter(m -> m.value() == 2.0).count();
        long halfCount = change.stream().filter(m -> m.value() == 0.5).count();
        
        assertEquals(1, fiveCount);
        assertEquals(1, twoCount);
        assertEquals(1, halfCount);
    }

    @Test
    void shouldCalculateChangeForComplexAmount() {
        List<Money> change = changeCalculator.calculateChange(18.5);
        
        assertNotNull(change);
        assertFalse(change.isEmpty());
        
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(18.5, totalChange, 0.01);
        
        long tenCount = change.stream().filter(m -> m.value() == 10.0).count();
        long fiveCount = change.stream().filter(m -> m.value() == 5.0).count();
        long twoCount = change.stream().filter(m -> m.value() == 2.0).count();
        long oneCount = change.stream().filter(m -> m.value() == 1.0).count();
        long halfCount = change.stream().filter(m -> m.value() == 0.5).count();
        
        assertEquals(1, tenCount);
        assertEquals(1, fiveCount);
        assertEquals(1, twoCount);
        assertEquals(1, oneCount);
        assertEquals(1, halfCount);
    }

    @Test
    void shouldCalculateChangeForZeroAmount() {
        List<Money> change = changeCalculator.calculateChange(0.0);
        
        assertNotNull(change);
        assertTrue(change.isEmpty());
    }

    @Test
    void shouldCalculateChangeForSmallAmount() {
        List<Money> change = changeCalculator.calculateChange(0.5);
        
        assertNotNull(change);
        assertEquals(1, change.size());
        assertEquals(0.5, change.get(0).value());
    }

    @Test
    void shouldCalculateChangeForLargeAmount() {
        List<Money> change = changeCalculator.calculateChange(50.0);
        
        assertNotNull(change);
        assertFalse(change.isEmpty());
        
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(50.0, totalChange, 0.01);
        
        long tenCount = change.stream().filter(m -> m.value() == 10.0).count();
        assertEquals(5, tenCount);
    }

    @Test
    void shouldUseGreedyAlgorithmOptimally() {
        List<Money> change = changeCalculator.calculateChange(3.5);
        
        assertNotNull(change);
        assertFalse(change.isEmpty());
        
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(3.5, totalChange, 0.01);
        
        long twoCount = change.stream().filter(m -> m.value() == 2.0).count();
        long oneCount = change.stream().filter(m -> m.value() == 1.0).count();
        long halfCount = change.stream().filter(m -> m.value() == 0.5).count();
        
        assertEquals(1, twoCount);
        assertEquals(1, oneCount);
        assertEquals(1, halfCount);
    }

    @Test
    void shouldCalculateChangeForMultipleOfSameDenomination() {
        List<Money> change = changeCalculator.calculateChange(4.0);
        
        assertNotNull(change);
        assertFalse(change.isEmpty());
        
        double totalChange = change.stream().mapToDouble(Money::value).sum();
        assertEquals(4.0, totalChange, 0.01);
        
        long twoCount = change.stream().filter(m -> m.value() == 2.0).count();
        assertEquals(2, twoCount);
    }
}
