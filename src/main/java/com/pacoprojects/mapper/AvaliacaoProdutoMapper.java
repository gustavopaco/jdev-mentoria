package com.pacoprojects.mapper;

import com.pacoprojects.dto.AvaliacaoProdutoDto;
import com.pacoprojects.model.AvaliacaoProduto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProdutoMapper.class, PessoaFisicaMapper.class, PessoaJuridicaMapper.class})
public interface AvaliacaoProdutoMapper {
    AvaliacaoProduto toEntity(AvaliacaoProdutoDto avaliacaoProdutoDto);

    AvaliacaoProdutoDto toDto(AvaliacaoProduto avaliacaoProduto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AvaliacaoProduto partialUpdate(AvaliacaoProdutoDto avaliacaoProdutoDto, @MappingTarget AvaliacaoProduto avaliacaoProduto);
}
