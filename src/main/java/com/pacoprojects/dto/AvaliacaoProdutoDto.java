package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.AvaliacaoProduto} entity
 */
public record AvaliacaoProdutoDto(

        Long id,

        @NotBlank(message = "Descrição obrigatório.")
        String descricao,

        @Range(min = 1, max = 10, message = "Nota deve ser no mínimo 1 e no máximo 10.")
        Integer nota,

        ProdutoDtoBasicId produto,
        PessoaFisicaDtoBasicId pessoa,
        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
