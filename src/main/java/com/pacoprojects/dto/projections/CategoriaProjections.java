package com.pacoprojects.dto.projections;

import com.pacoprojects.model.Produto;

import java.util.Set;

/**
 * A Projection for the {@link com.pacoprojects.model.Categoria} entity
 */
public interface CategoriaProjections {
    Long getId();

    String getNome();

    Set<Produto> getProdutos();

    PessoaJuridicaProjection getEmpresa();
}
