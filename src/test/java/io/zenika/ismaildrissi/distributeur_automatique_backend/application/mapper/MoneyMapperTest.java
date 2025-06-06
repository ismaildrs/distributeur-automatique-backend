package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.MoneyDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyMapperTest {

    private MoneyMapper moneyMapper;

    @BeforeEach
    void setUp() {
        moneyMapper = new MoneyMapper();
    }

    @Test
    void shouldMapMoneyDTOToDomain() {
        MoneyDTO moneyDTO = new MoneyDTO(5.0);
        
        Money result = moneyMapper.toDomain(moneyDTO);
        
        assertNotNull(result);
        assertEquals(5.0, result.value());
    }

    @Test
    void shouldMapMoneyToDTO() {
        Money money = new Money(2.0);
        
        MoneyDTO result = moneyMapper.toDTO(money);
        
        assertNotNull(result);
        assertEquals(2.0, result.getValue());
    }

    @Test
    void shouldMapAllValidMoneyValues() {
        double[] validValues = {0.5, 1.0, 2.0, 5.0, 10.0};
        
        for (double value : validValues) {
            MoneyDTO dto = new MoneyDTO(value);
            Money domain = moneyMapper.toDomain(dto);
            MoneyDTO backToDto = moneyMapper.toDTO(domain);
            
            assertEquals(value, domain.value());
            assertEquals(value, backToDto.getValue());
        }
    }

    @Test
    void shouldPreserveValueInBothDirections() {
        MoneyDTO originalDTO = new MoneyDTO(1.0);
        Money domain = moneyMapper.toDomain(originalDTO);
        MoneyDTO resultDTO = moneyMapper.toDTO(domain);
        
        assertEquals(originalDTO.getValue(), resultDTO.getValue());
    }
}
