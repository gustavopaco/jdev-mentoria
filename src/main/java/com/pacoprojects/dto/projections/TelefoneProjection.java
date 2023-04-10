package com.pacoprojects.dto.projections;

import com.pacoprojects.model.Telefone;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.Telefone} entity
 */
@Projection(name = "telefoneProjection", types = Telefone.class)
public interface TelefoneProjection {
    Long getId();

    String getNumero();
}
