package io.zenika.ismaildrissi.distributeur_automatique_backend.infrastructure.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.product.Product;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MoneyMapper {

    ModelMapper modelMapper;

    public Money toDomain(MoneyDTO moneyDTO){
        return modelMapper.map(moneyDTO, Money.class);
    }

    public MoneyDTO toDTO(Money money){
        return modelMapper.map(money, MoneyDTO.class);
    }

}




