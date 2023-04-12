package com.pacoprojects.dto.projections;

import com.pacoprojects.model.FormaPagamento;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.FormaPagamento} entity
 */
@Projection(name = "formaPagamentoProjection", types = FormaPagamento.class)
public interface FormaPagamentoProjection {
    Long getId();

    String getDescricao();

    PessoaJuridicaProjection getEmpresa();
}
