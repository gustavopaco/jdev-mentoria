package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Telefone} entity
 */
@Builder
public record TelefoneDto(

        Long id,

        @NotBlank(message = "Número obrigatório.")
        String numero

) implements Serializable {
}
