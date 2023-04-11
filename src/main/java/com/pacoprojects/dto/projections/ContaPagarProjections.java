package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.StatusContaPagar;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.ContaPagar} entity
 */
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
