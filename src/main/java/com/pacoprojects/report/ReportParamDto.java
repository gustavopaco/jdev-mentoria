package com.pacoprojects.report;

import com.pacoprojects.enums.StatusVendaCompra;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ReportParamDto(

        // Produto Params
        Long codigoProduto,
        String nomeProduto,

        // Fornecedor Params
        Long codigoFornecedor,

        // Cliente
        Long codigoCliente,
        String nomeCliente,

        // Empresa Params
        Long codigoEmpresa,

        // Venda Params
        Long codigoVendaCompra,
        StatusVendaCompra statusVendaCompra,

        // NotaFiscal Params
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
