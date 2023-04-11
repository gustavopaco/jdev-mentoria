package com.pacoprojects.dto.projections;

import com.pacoprojects.model.Produto;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Set;

/**
 * A Projection for the {@link com.pacoprojects.model.Produto} entity
 */
@Projection(name = "produtoProjections", types = Produto.class)
public interface ProdutoProjections {
    Long getId();

    String getTipoUnidade();

    String getNome();

    String getDescricao();

    Double getPeso();

    Double getLargura();

    Double getAltura();

    Double getProfundidade();

    BigDecimal getValorVenda();

    Integer getQuantidadeEstoque();

    Integer getQuantidadeAlertaEstoque();

    String getLinkYoutube();

    Integer getQuantidadeClick();

    Boolean isAlertaEstoqueEnabled();

    Boolean isEnabled();

    MarcaProdutoProjections getMarcaProduto();

    Set<CategoriaProjections> getCategorias();

    PessoaJuridicaProjection getEmpresa();
}
