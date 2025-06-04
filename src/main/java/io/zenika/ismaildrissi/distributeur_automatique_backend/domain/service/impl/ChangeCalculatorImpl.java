package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.impl;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service.ChangeCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Used to calculate the change using VALID MONEY in MAD
 */
public class ChangeCalculatorImpl implements ChangeCalculator {

    private static final List<Double> VALID_DENOMINATIONS = List.of(10.0, 5.0, 2.0, 1.0, 0.5);

    /**
     * Calculates change using Greedy Algorithm
     * @param changeAmount
     * @return
     */
    public List<Money> calculateChange(double changeAmount) {
        List<Money> change = new ArrayList<>();

        for (double denom : VALID_DENOMINATIONS) {
            while (changeAmount >= denom) {
                change.add(new Money(denom));
                changeAmount = changeAmount - denom;
            }
        }

        return change;
    }
}

