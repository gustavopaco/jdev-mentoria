package com.pacoprojects.dto.projections;

import com.pacoprojects.model.NotaFiscalCompra;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.NotaFiscalCompra} entity
 */
@Projection(name = "notaFiscalcompraProjections", types = NotaFiscalCompra.class)
public interface NotaFiscalCompraProjections {
    Long getId();

    String getNumero();

    String getSerie();

    String getDescricao();

    BigDecimal getValorTotal();

    BigDecimal getValorDesconto();

    BigDecimal getValorIcms();

    LocalDate getDataCompra();

    PessoaJuridicaProjection getPessoa();

    ContaPagarProjections getContaPagar();

    PessoaJuridicaProjection getEmpresa();
}
