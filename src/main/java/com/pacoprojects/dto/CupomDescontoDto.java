package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.pacoprojects.model.CupomDesconto} entity
 */
public record CupomDescontoDto(

        Long id,

        @NotBlank(message = "Código do cupom obrigatório.")
        String codigoDescricao,

        BigDecimal valorRealDesconto,
        BigDecimal valorPorcentagemDescricao,

        @NotNull(message = "Data de validade obrigatório.")
        LocalDate validadeCupom,

        @NotNull(message = "Empresa deve ser informado.")

        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
