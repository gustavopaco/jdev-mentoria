package com.pacoprojects.report;

import com.pacoprojects.repository.NotaFiscalCompraRepository;
import com.pacoprojects.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final NotaFiscalCompraRepository repositoryNotaFiscalCompra;
    private final ProdutoRepository repositoryProduto;

    /** Title: Historico de Compras de produtos para a loja
     * Esse relatorio permite saber os produtos que foram comprados presente em uma Nota Fiscal de Compra
     * @param notaFiscalDto @apiNote
     * @return List<ReportNotaFiscalProjection>
     */
    public List<ReportNotaFiscalProjection> relatorioProdutoPorNotaFiscal(ReportNotaFiscalDto notaFiscalDto) {
        return repositoryNotaFiscalCompra.relatorioProdutoPorNotaFiscal(
                notaFiscalDto.dataInicial(),
                notaFiscalDto.dataFinal(),
                notaFiscalDto.codigoProduto(),
                notaFiscalDto.nomeProduto(),
                notaFiscalDto.codigoFornecedor(),
                notaFiscalDto.codigoNota());
    }

    /**
     * Esse relatorio retorna uma lista de produtos que estao com estoque baixo de acordo com a quantidade para alerta do estoque
     * @param idEmpresa @apiNote
     * @return List<ReportProdutoLowStockProjection>
     */
    public List<ReportProdutoLowStockProjection> relatorioProdutoEstoqueBaixo(Long idEmpresa) {
        return repositoryProduto.relatorioProdutoEstoqueBaixo(idEmpresa);
    }
}
