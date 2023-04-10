package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.model.Endereco;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.Endereco} entity
 */
@Projection(name = "enderecoProjection", types = Endereco.class)
public interface EnderecoProjection {
    Long getId();

    String getRua();

    String getCep();

    String getNumero();

    String getComplemento();

    String getBairro();

    String getCidade();

    String getEstado();

    TipoEndereco getTipoEndereco();
}
