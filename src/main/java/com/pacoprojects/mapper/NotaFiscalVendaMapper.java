package com.pacoprojects.mapper;

import com.pacoprojects.dto.NotaFiscalVendaDto;
import com.pacoprojects.dto.NotaFiscalVendaDtoBasicId;
import com.pacoprojects.model.NotaFiscalVenda;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotaFiscalVendaMapper {
    NotaFiscalVenda toEntity1(NotaFiscalVendaDtoBasicId notaFiscalVendaDtoBasicId);

    NotaFiscalVendaDtoBasicId toDto1(NotaFiscalVenda notaFiscalVenda);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotaFiscalVenda partialUpdate1(NotaFiscalVendaDtoBasicId notaFiscalVendaDtoBasicId, @MappingTarget NotaFiscalVenda notaFiscalVenda);

    NotaFiscalVenda toEntity(NotaFiscalVendaDto notaFiscalVendaDto);

    NotaFiscalVendaDto toDto(NotaFiscalVenda notaFiscalVenda);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotaFiscalVenda partialUpdate(NotaFiscalVendaDto notaFiscalVendaDto, @MappingTarget NotaFiscalVenda notaFiscalVenda);
}
