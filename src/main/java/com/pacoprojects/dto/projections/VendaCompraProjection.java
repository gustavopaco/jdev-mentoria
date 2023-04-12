package com.pacoprojects.dto.projections;

import com.pacoprojects.model.VendaCompra;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
 */
@Projection(name = "vendaCompraProjection", types = VendaCompra.class)
public interface VendaCompraProjection {
    Long getId();

    BigDecimal getValorTotal();

    BigDecimal getValorDesconto();

    BigDecimal getValorFrete();

    Integer getDiasParaEntrega();

    LocalDate getDataVenda();

    LocalDate getDataEntrega();

    PessoaFisicaProjection getPessoa();

    EnderecoProjection getEnderecoEntrega();

    EnderecoProjection getEnderecoCobranca();

    PessoaJuridicaProjection getEmpresa();
}
