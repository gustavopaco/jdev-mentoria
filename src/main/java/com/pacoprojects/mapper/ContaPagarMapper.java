package com.pacoprojects.mapper;

import com.pacoprojects.dto.ContaPagarDto;
import com.pacoprojects.dto.ResponseContaPagarDto;
import com.pacoprojects.model.ContaPagar;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContaPagarMapper {
    ContaPagar toEntity(ContaPagarDto contaPagarDto);

    ContaPagarDto toDto(ContaPagar contaPagar);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContaPagar partialUpdate(ContaPagarDto contaPagarDto, @MappingTarget ContaPagar contaPagar);

    ContaPagar toEntity1(ResponseContaPagarDto responseContaPagarDto);

    ResponseContaPagarDto toDto1(ContaPagar contaPagar);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContaPagar partialUpdate1(ResponseContaPagarDto responseContaPagarDto, @MappingTarget ContaPagar contaPagar);
}
