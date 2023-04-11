package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.ImagemProduto} entity
 */
public record ImagemProdutoDto(

        Long id,

        @NotBlank(message = "Imagem original obrigatório.")
        String imagemOriginal,

        @NotBlank(message = "Imagem miniatura obrigatório")
        String imagemMiniatura

) implements Serializable {
}
