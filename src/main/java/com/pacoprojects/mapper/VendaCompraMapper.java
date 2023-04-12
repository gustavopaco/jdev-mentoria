package com.pacoprojects.mapper;

import com.pacoprojects.dto.VendaCompraDto;
import com.pacoprojects.dto.VendaCompraDtoBasicId;
import com.pacoprojects.model.VendaCompra;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VendaCompraMapper {
    VendaCompra toEntity(VendaCompraDto vendaCompraDto);

    VendaCompra toEntity1(VendaCompraDtoBasicId vendaCompraDtoBasicId);

    VendaCompraDto toDto(VendaCompra vendaCompra);

    VendaCompraDtoBasicId toDto1(VendaCompra vendaCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendaCompra partialUpdate(VendaCompraDto vendaCompraDto, @MappingTarget VendaCompra vendaCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendaCompra partialUpdate1(VendaCompraDtoBasicId vendaCompraDtoBasicId, @MappingTarget VendaCompra vendaCompra);
}
