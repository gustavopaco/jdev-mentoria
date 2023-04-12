package com.pacoprojects.dto.projections;

import com.pacoprojects.model.AvaliacaoProduto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.AvaliacaoProduto} entity
 */
@Projection(name = "avaliacaoProdutoProjection", types = AvaliacaoProduto.class)
public interface AvaliacaoProdutoProjection {
    Long getId();

    String getDescricao();

    Integer getNota();

    ProdutoProjectionBaseId getProduto();

    PessoaFisicaProjection getPessoa();

    PessoaJuridicaProjection getEmpresa();
}
