package com.pacoprojects.report;

import com.pacoprojects.model.Produto;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.Produto} entity
 */
@Projection(name = "reportProdutoLowStockProjection", types = Produto.class)
public interface ReportProdutoLowStockProjection {


    String getCodigoDonoEmpresa();

    String getNomeDonoEmpresa();

    String getEmailDonoEmpresa();

    String getCodigoProduto();

    String getProdutoNome();

    Integer getQuantidadeEstoque();

    Integer getQuantidadeAlertaEstoque();

    String getCodigoFornecedor();

    String getNomeFornecedor();

    String getEmailFornecedor();
}
