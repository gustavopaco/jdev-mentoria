package com.pacoprojects.mapper;

import com.pacoprojects.dto.CupomDescontoDto;
import com.pacoprojects.dto.CupomDescontoDtoBasicId;
import com.pacoprojects.model.CupomDesconto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CupomDescontoMapper {
    CupomDesconto toEntity(CupomDescontoDto cupomDescontoDto);

    CupomDesconto toEntity1(CupomDescontoDtoBasicId cupomDescontoDtoBasicId);

    CupomDescontoDto toDto(CupomDesconto cupomDesconto);

    CupomDescontoDtoBasicId toDto1(CupomDesconto cupomDesconto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CupomDesconto partialUpdate(CupomDescontoDto cupomDescontoDto, @MappingTarget CupomDesconto cupomDesconto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CupomDesconto partialUpdate1(CupomDescontoDtoBasicId cupomDescontoDtoBasicId, @MappingTarget CupomDesconto cupomDesconto);

    CupomDesconto toEntity2(CupomDescontoDtoBasicId cupomDescontoDtoBasicId);
}
