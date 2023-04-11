package com.pacoprojects.dto;

import com.pacoprojects.model.Categoria;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link Categoria} entity
 */
public record CategoriaDto(

        Long id,

        @NotBlank(message = "Nome da Categoria obrigatório")
        String nome,

        PessoaJuridicaDto empresa

) implements Serializable {
}
