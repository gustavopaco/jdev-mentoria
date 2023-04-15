package com.pacoprojects.report;

import com.pacoprojects.repository.NotaFiscalCompraRepository;
import com.pacoprojects.repository.ProdutoRepository;
import com.pacoprojects.repository.VendaCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final NotaFiscalCompraRepository repositoryNotaFiscalCompra;
    private final ProdutoRepository repositoryProduto;
    private final VendaCompraRepository repositoryVendaCompra;

    /** Title: Historico de Compras de produtos para a loja
     * Esse relatorio permite saber os produtos que foram comprados presente em uma Nota Fiscal de Compra
     * @param notaFiscalDto @apiNote
     * @return List<ReportNotaFiscalProjection>
     */
    public List<ReportNotaFiscalProjection> relatorioProdutoPorNotaFiscal(ReportParamDto notaFiscalDto) {
        return repositoryNotaFiscalCompra.relatorioProdutoPorNotaFiscal(
                notaFiscalDto.dataInicial(),
                notaFiscalDto.dataFinal(),
                notaFiscalDto.codigoProduto(),
                notaFiscalDto.nomeProduto(),
                notaFiscalDto.codigoFornecedor(),
                notaFiscalDto.codigoNota());
    }

    /** Title: Relatorio de Produto com estoque baixo
     * Esse relatorio retorna uma lista de produtos que estao com estoque baixo de acordo com a quantidade para alerta do estoque
     * @param idEmpresa @apiNote
     * @return List<ReportProdutoLowStockProjection>
     */
    public List<ReportProdutoLowStockProjection> relatorioProdutoEstoqueBaixo(Long idEmpresa) {
        return repositoryProduto.relatorioProdutoEstoqueBaixo(idEmpresa);
    }

    /** Title: Relatorio de Venda Cancelada por intervalo de datas
     * Esse relatorio permite saber vendas de uma empresa com status cancelado, pode ser adicionado outros parametros na consulta
     * @param reportParamDto @apiNote
     * @return List<ReportVendaCanceladaProjection>
     */
    public List<ReportVendaCanceladaProjection> relatorioVendaCancelada(ReportParamDto reportParamDto) {
        return repositoryVendaCompra.relatorioVendaCancelada(
                reportParamDto.codigoEmpresa(),
                reportParamDto.dataInicial(),
                reportParamDto.dataFinal());
    }
}
