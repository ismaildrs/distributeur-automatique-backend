package io.zenika.ismaildrissi.distributeur_automatique_backend.domain.service;

import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;

import java.util.List;

public interface ChangeCalculator{
    List<Money> calculateChange(double changeAmount);
}