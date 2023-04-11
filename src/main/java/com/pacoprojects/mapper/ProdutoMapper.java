package com.pacoprojects.mapper;

import com.pacoprojects.dto.ProdutoDto;
import com.pacoprojects.dto.ProdutoDtoBasicId;
import com.pacoprojects.model.Produto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProdutoMapper {
    Produto toEntity(ProdutoDto produtoDto);

    ProdutoDto toDto(Produto produto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Produto partialUpdate(ProdutoDto produtoDto, @MappingTarget Produto produto);

    Produto toEntity1(ProdutoDtoBasicId produtoDtoBasicId);

    ProdutoDtoBasicId toDto1(Produto produto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Produto partialUpdate1(ProdutoDtoBasicId produtoDtoBasicId, @MappingTarget Produto produto);

    @AfterMapping
    default void linkImagens(@MappingTarget Produto produto) {
        produto.getImagemProdutos().forEach(imagemProduto -> {
            imagemProduto.setProduto(produto);
            imagemProduto.setEmpresa(produto.getEmpresa());
        });
    }
}
