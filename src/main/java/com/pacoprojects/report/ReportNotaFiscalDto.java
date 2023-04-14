package com.pacoprojects.report;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReportNotaFiscalDto(

        Long codigoProduto,
        String nomeProduto,
        Long codigoFornecedor,
        Long codigoNota,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataCompra,

        @NotNull(message = "Informa a data inicial")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataInicial,
        @NotNull(message = "Informe a data final")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
        LocalDate dataFinal


) {
}
