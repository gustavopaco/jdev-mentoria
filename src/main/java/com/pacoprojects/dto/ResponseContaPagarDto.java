package com.pacoprojects.dto;

import com.pacoprojects.enums.StatusContaPagar;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.pacoprojects.model.ContaPagar} entity
 */
public record ResponseContaPagarDto(

        Long id,

        @NotBlank(message = "Descrição da conta a pagar obrigatório.")
        String descricao,

        @NotNull(message = "Status da conta obrigatório.")
        StatusContaPagar status,
        @Future(message = "Data de vencimento deve ser a partir do dia atual.")
        @NotNull(message = "Data de vencimento obrigatório.")
        LocalDate dataVencimento,

        LocalDate dataPagamento,

        @NotNull(message = "Valor total obrigatório.") BigDecimal valorTotal,
        BigDecimal valorDesconto,

        @NotNull(message = "Pessoa deve ser informada.")
        PessoaJuridicaDto pessoa,

        @NotNull(message = "Fornecedor responsável deve ser informado.")
        PessoaJuridicaDto pessoaFornecedor,

        @NotNull(message = "Empresa deve ser informado.")
        PessoaJuridicaDto empresa

) implements Serializable {
}
