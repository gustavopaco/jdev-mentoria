package com.pacoprojects.dto;

import com.pacoprojects.enums.TipoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.Endereco} entity
 */
public record EnderecoDto(Long id, @NotBlank(message = "Rua obrigatório.") String rua,
                          @NotBlank(message = "Cep obrigatório.") String cep,
                          @NotBlank(message = "Número obrigatório.") String numero, String complemento,
                          @NotBlank(message = "Bairro obrigatório.") String bairro,
                          @NotBlank(message = "Cidade obrigatório.") String cidade,
                          @NotBlank(message = "Estado obrigatório.") String estado,
                          @NotNull(message = "Tipo de endereço obrigatório.") TipoEndereco tipoEndereco) implements Serializable {
}
