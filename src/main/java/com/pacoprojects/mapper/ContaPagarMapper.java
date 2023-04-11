package com.pacoprojects.mapper;

import com.pacoprojects.dto.ContaPagarDto;
import com.pacoprojects.dto.ContaPagarDtoBasicId;
import com.pacoprojects.model.ContaPagar;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContaPagarMapper {
    ContaPagar toEntity(ContaPagarDto contaPagarDto);

    ContaPagarDto toDto(ContaPagar contaPagar);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContaPagar partialUpdate(ContaPagarDto contaPagarDto, @MappingTarget ContaPagar contaPagar);

    ContaPagar toEntity1(ContaPagarDtoBasicId contaPagarDtoBasicId);

    ContaPagarDtoBasicId toDto1(ContaPagar contaPagar);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContaPagar partialUpdate1(ContaPagarDtoBasicId contaPagarDtoBasicId, @MappingTarget ContaPagar contaPagar);
}
