package com.pacoprojects.dto.projections;

import com.pacoprojects.model.Produto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.Produto} entity
 */
@Projection(name = "produtoProjections", types = Produto.class)
public interface ProdutoProjectionBaseId {
    Long getId();
}
