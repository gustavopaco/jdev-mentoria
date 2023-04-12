package com.pacoprojects.dto.projections;

import com.pacoprojects.model.ImagemProduto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.ImagemProduto} entity
 */
@Projection(name = "imagemProdutoProjections", types = ImagemProduto.class)
public interface ImagemProdutoProjections {
    Long getId();

    String getImagemOriginal();

    String getImagemMiniatura();

    PessoaJuridicaProjection getEmpresa();

    ProdutoProjectionBaseId getProduto();
}
