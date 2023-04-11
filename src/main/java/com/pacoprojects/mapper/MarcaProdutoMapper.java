package com.pacoprojects.mapper;

import com.pacoprojects.dto.MarcaProdutoDto;
import com.pacoprojects.dto.MarcaProdutoDtoBasicId;
import com.pacoprojects.model.MarcaProduto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MarcaProdutoMapper {
    MarcaProduto toEntity(MarcaProdutoDto marcaProdutoDto);

    MarcaProdutoDto toDto(MarcaProduto marcaProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MarcaProduto partialUpdate(MarcaProdutoDto marcaProdutoDto, @MappingTarget MarcaProduto marcaProduto);

    MarcaProduto toEntity1(MarcaProdutoDtoBasicId marcaProdutoDtoBasicId);

    MarcaProdutoDtoBasicId toDto1(MarcaProduto marcaProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MarcaProduto partialUpdate1(MarcaProdutoDtoBasicId marcaProdutoDtoBasicId, @MappingTarget MarcaProduto marcaProduto);
}
