package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.StatusContaPagar;
import com.pacoprojects.model.ContaPagar;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.ContaPagar} entity
 */
@Projection(name = "contaPagarProjections", types = ContaPagar.class)
public interface ContaPagarProjections {
    Long getId();

    String getDescricao();

    StatusContaPagar getStatus();

    LocalDate getDataVencimento();

    LocalDate getDataPagamento();

    BigDecimal getValorTotal();

    BigDecimal getValorDesconto();

    PessoaJuridicaProjection getPessoa();

    PessoaJuridicaProjection getPessoaFornecedor();

    PessoaJuridicaProjection getEmpresa();
}
