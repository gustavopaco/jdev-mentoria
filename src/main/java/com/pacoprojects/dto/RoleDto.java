package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Role} entity
 */
@Builder
public record RoleDto(

        Long id,

        @NotBlank(message = "Role obrigatório.")
        String authority

) implements Serializable {
}
