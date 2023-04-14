package com.pacoprojects.report;

import com.pacoprojects.repository.NotaFiscalCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final NotaFiscalCompraRepository repositoryNotaFiscalCompra;

    public List<ReportNotaFiscalProjection> relatorioProdutoPorNotaFiscal(ReportNotaFiscalDto notaFiscalDto) {
        return repositoryNotaFiscalCompra.relatorioProdutoPorNotaFiscal(
                notaFiscalDto.dataInicial(),
                notaFiscalDto.dataFinal(),
                notaFiscalDto.codigoProduto(),
                notaFiscalDto.nomeProduto(),
                notaFiscalDto.codigoFornecedor(),
                notaFiscalDto.codigoNota());
    }
}
