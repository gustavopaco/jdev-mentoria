package com.pacoprojects.dto.projections;

import com.pacoprojects.model.ItemVendaCompra;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.ItemVendaCompra} entity
 */
@Projection(name = "itemVendaComraProjections", types = ItemVendaCompra.class)
public interface ItemVendaCompraProjections {
    Long getId();

    Double getQuantidade();

    ProdutoProjections getProduto();

//    VendaCompraProjectionBasicId getVendaCompra();

//    PessoaJuridicaProjection getEmpresa();
}
