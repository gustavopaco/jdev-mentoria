package com.pacoprojects.mapper;

import com.pacoprojects.dto.NotaFiscalCompraDto;
import com.pacoprojects.dto.NotaFiscalCompraDtoBasicId;
import com.pacoprojects.model.NotaFiscalCompra;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotaFiscalCompraMapper {
    NotaFiscalCompra toEntity(NotaFiscalCompraDto notaFiscalCompraDto);

    NotaFiscalCompraDto toDto(NotaFiscalCompra notaFiscalCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotaFiscalCompra partialUpdate(NotaFiscalCompraDto notaFiscalCompraDto, @MappingTarget NotaFiscalCompra notaFiscalCompra);

    NotaFiscalCompra toEntity1(NotaFiscalCompraDtoBasicId notaFiscalCompraDtoBasicId);

    NotaFiscalCompraDtoBasicId toDto1(NotaFiscalCompra notaFiscalCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    NotaFiscalCompra partialUpdate1(NotaFiscalCompraDtoBasicId notaFiscalCompraDtoBasicId, @MappingTarget NotaFiscalCompra notaFiscalCompra);
}
