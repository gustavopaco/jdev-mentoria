package com.pacoprojects.dto;

import com.pacoprojects.model.FormaPagamento;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link FormaPagamento} entity
 */
public record FormaPagamentoDto(
        Long id,

        @NotBlank(message = "Descrição obrigatório.")
        String descricao,

        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
