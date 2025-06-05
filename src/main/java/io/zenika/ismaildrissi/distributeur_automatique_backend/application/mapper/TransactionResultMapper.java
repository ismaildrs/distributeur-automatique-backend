package io.zenika.ismaildrissi.distributeur_automatique_backend.application.mapper;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.TransactionResultDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.domain.model.transaction.TransactionResult;
import org.modelmapper.ModelMapper;

public class TransactionResultMapper {

    ModelMapper modelMapper;

    public TransactionResult toDomain(TransactionResultDTO transactionResultDTO){
        return modelMapper.map(transactionResultDTO, TransactionResult.class);
    }

    public TransactionResultDTO toDTO(TransactionResult transactionResult){
        return modelMapper.map(transactionResult, TransactionResultDTO.class);
    }

}
