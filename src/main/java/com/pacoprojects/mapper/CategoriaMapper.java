package com.pacoprojects.mapper;

import com.pacoprojects.dto.CategoriaDto;
import com.pacoprojects.model.Categoria;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    Categoria toEntity(CategoriaDto categoriaDto);

    CategoriaDto toDto(Categoria categoria);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Categoria partialUpdate(CategoriaDto categoriaDto, @MappingTarget Categoria categoria);
}
