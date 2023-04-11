package com.pacoprojects.mapper;

import com.pacoprojects.dto.ItemNotaProdutoDto;
import com.pacoprojects.dto.ItemNotaProdutoDtoBasicId;
import com.pacoprojects.model.ItemNotaProduto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemNotaProdutoMapper {
    ItemNotaProduto toEntity(ItemNotaProdutoDtoBasicId itemNotaProdutoDtoBasicId);

    ItemNotaProdutoDtoBasicId toDto(ItemNotaProduto itemNotaProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemNotaProduto partialUpdate(ItemNotaProdutoDtoBasicId itemNotaProdutoDtoBasicId, @MappingTarget ItemNotaProduto itemNotaProduto);

    ItemNotaProduto toEntity1(ItemNotaProdutoDto itemNotaProdutoDto);

    ItemNotaProdutoDto toDto1(ItemNotaProduto itemNotaProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ItemNotaProduto partialUpdate1(ItemNotaProdutoDto itemNotaProdutoDto, @MappingTarget ItemNotaProduto itemNotaProduto);
}
