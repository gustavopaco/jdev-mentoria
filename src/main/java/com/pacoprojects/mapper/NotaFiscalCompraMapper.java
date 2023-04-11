package com.pacoprojects.mapper;

import com.pacoprojects.dto.NotaFiscalCompraDto;
import com.pacoprojects.model.NotaFiscalCompra;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotaFiscalCompraMapper {
    NotaFiscalCompra toEntity(NotaFiscalCompraDto notaFiscalCompraDto);

    NotaFiscalCompraDto toDto(NotaFiscalCompra notaFiscalCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotaFiscalCompra partialUpdate(NotaFiscalCompraDto notaFiscalCompraDto, @MappingTarget NotaFiscalCompra notaFiscalCompra);
}
