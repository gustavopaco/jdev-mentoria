package com.pacoprojects.mapper;

import com.pacoprojects.dto.FormaPagamentoDto;
import com.pacoprojects.dto.FormaPagamentoDtoBasicId;
import com.pacoprojects.model.FormaPagamento;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormaPagamentoMapper {
    FormaPagamento toEntity(FormaPagamentoDto formaPagamentoDto);

    FormaPagamentoDto toDto(FormaPagamento formaPagamento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FormaPagamento partialUpdate(FormaPagamentoDto formaPagamentoDto, @MappingTarget FormaPagamento formaPagamento);

    FormaPagamento toEntity1(FormaPagamentoDtoBasicId formaPagamentoDtoBasicId);

    FormaPagamentoDtoBasicId toDto1(FormaPagamento formaPagamento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FormaPagamento partialUpdate1(FormaPagamentoDtoBasicId formaPagamentoDtoBasicId, @MappingTarget FormaPagamento formaPagamento);
}
