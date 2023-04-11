package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.pacoprojects.model.NotaFiscalCompra} entity
 */
public record NotaFiscalCompraDto(

        Long id,

        @NotBlank(message = "Número da nota obrigatório.")
        String numero,

        @NotBlank(message = "Série da nota obrigatório.")
        String serie,

        String descricao,
        @NotNull(message = "Valor total obrigatório.")
        BigDecimal valorTotal,

        BigDecimal valorDesconto,

        @NotNull(message = "Valor de ICMS obrigatório.")
        BigDecimal valorIcms,

        @NotNull(message = "Data da compra obrigatório.")
        LocalDate dataCompra,

        @NotNull(message = "Pessoa deve ser informada.")
        PessoaJuridicaDtoBasicId pessoa,

        @NotNull(message = "Fornecedor responsável deve ser informado.")
        ContaPagarDtoBasicId contaPagar,

        @NotNull(message = "Empresa deve ser informada.")
        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
