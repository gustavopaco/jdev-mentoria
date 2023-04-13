package com.pacoprojects.mapper;

import com.pacoprojects.dto.ItemVendaCompraDto;
import com.pacoprojects.model.ItemVendaCompra;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemVendaCompraMapper {
    ItemVendaCompra toEntity(ItemVendaCompraDto itemVendaCompraDto);

    ItemVendaCompraDto toDto(ItemVendaCompra itemVendaCompra);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemVendaCompra partialUpdate(ItemVendaCompraDto itemVendaCompraDto, @MappingTarget ItemVendaCompra itemVendaCompra);
}
