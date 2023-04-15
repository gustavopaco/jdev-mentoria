package com.pacoprojects.report;

import com.pacoprojects.enums.StatusVendaCompra;
import com.pacoprojects.model.VendaCompra;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
 */

@Projection(name = "reportVendaCanceladaProjection", types = VendaCompra.class)
public interface ReportVendaCanceladaProjection {


    Long getIdVenda();

    Long getIdCliente();

    String getNomeCliente();

    List<String> getTelefoneCliente();

    String getEmailCliente();

    Integer getQuantidadeProduto();

    Long getIdProduto();

    String getProdutoNome();

    StatusVendaCompra getStatusVenda();
}
