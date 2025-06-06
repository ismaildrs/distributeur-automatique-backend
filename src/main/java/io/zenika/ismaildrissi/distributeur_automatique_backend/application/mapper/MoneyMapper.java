package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper component for converting between Money domain objects and MoneyDTO.
 *
 * <p>This mapper handles the bidirectional conversion between the domain layer's
 * Money value object and the application layer's MoneyDTO. It ensures proper
 * data transformation while maintaining the integrity of monetary values.</p>
 *
 * <p>The mapper abstracts the conversion logic and provides a centralized place
 * for any future mapping customizations or validations that might be needed.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@Component
@AllArgsConstructor
public class MoneyMapper {

    /**
     * Converts a MoneyDTO to a Money domain object.
     *
     * @param moneyDTO the DTO to convert
     * @return the corresponding Money domain object
     */
    public Money toDomain(MoneyDTO moneyDTO){
        return new Money(moneyDTO.getValue());
    }

    /**
     * Converts a Money domain object to a MoneyDTO.
     *
     * @param money the domain object to convert
     * @return the corresponding MoneyDTO
     */
    public MoneyDTO toDTO(Money money){
        return new MoneyDTO(money.value());
    }

}




