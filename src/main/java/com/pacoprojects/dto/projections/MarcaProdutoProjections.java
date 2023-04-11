package com.pacoprojects.dto.projections;

import com.pacoprojects.model.MarcaProduto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.MarcaProduto} entity
 */
@Projection(name = "marcaProdutoProjections", types = MarcaProduto.class)
public interface MarcaProdutoProjections {
    Long getId();

    String getNome();

    PessoaJuridicaProjection getEmpresa();


}
