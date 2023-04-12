package com.pacoprojects.mapper;

import com.pacoprojects.dto.ImagemProdutoDto;
import com.pacoprojects.dto.ImagemProdutoDtoRegister;
import com.pacoprojects.model.ImagemProduto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProdutoMapper.class, PessoaJuridicaMapper.class})
public interface ImagemProdutoMapper {
    ImagemProduto toEntity(ImagemProdutoDtoRegister imagemProdutoDtoRegister);

    ImagemProduto toEntity1(ImagemProdutoDto imagemProdutoDto);

    ImagemProdutoDtoRegister toDto(ImagemProduto imagemProduto);

    ImagemProdutoDto toDto1(ImagemProduto imagemProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ImagemProduto partialUpdate(ImagemProdutoDtoRegister imagemProdutoDtoRegister, @MappingTarget ImagemProduto imagemProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ImagemProduto partialUpdate1(ImagemProdutoDto imagemProdutoDto, @MappingTarget ImagemProduto imagemProduto);
}
