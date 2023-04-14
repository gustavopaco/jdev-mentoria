package com.pacoprojects.report;

import com.pacoprojects.model.NotaFiscalCompra;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.NotaFiscalCompra} entity
 */
@Projection(name = "reportNotaFiscalProjection", types = NotaFiscalCompra.class)
public interface ReportNotaFiscalProjection {


    Long getCodigoProduto();


    String getNomeProduto();


    BigDecimal getValorVendaProduto();


    Double getQuantidadeComprado();


    Long getCodigoFornecedor();

    String getNomeFornecedor();


    LocalDate getDataCompra();
}
