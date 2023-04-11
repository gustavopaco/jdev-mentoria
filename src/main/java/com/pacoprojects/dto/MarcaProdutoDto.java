package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.MarcaProduto} entity
 */
public record MarcaProdutoDto(

        Long id,

        @NotBlank(message = "Nome da marca é obrigatório.")
        String nome,

        @NotNull(message = "Empresa deve ser informada.")
        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
