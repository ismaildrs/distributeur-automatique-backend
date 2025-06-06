package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MoneyMapper {

    public Money toDomain(MoneyDTO moneyDTO){
        return new Money(moneyDTO.getValue());
    }

    public MoneyDTO toDTO(Money money){
        return new MoneyDTO(money.value());
    }

}




