package com.pacoprojects.auth;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Usuario} entity
 */
public record AuthenticateRequest(@NotBlank(message = "Login obrigatório.") String username,
                                  @NotBlank(message = "Senha obrigatório.") String password) implements Serializable {
}
