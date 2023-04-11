package com.pacoprojects.dto;

import com.pacoprojects.model.MarcaProduto;

import java.io.Serializable;

/**
 * A DTO for the {@link MarcaProduto} entity
 */
public record MarcaProdutoDtoBasicId(Long id) implements Serializable {
}
