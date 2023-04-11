package com.pacoprojects.dto.projections;

import com.pacoprojects.model.ItemNotaProduto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.ItemNotaProduto} entity
 */
@Projection(name = "itemNotaProdutoProjections", types = ItemNotaProduto.class)
public interface ItemNotaProdutoProjections {
    Long getId();

    Double getQuantidade();

    NotaFiscalCompraProjections getNotaFiscalCompra();

    PessoaJuridicaProjection getEmpresa();

    ProdutoProjections getProduto();

}
