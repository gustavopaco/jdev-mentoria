package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.PessoaFisica} entity
 */
public record PessoaFisicaDto(

        Long id,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "E-mail obrigatório.")
        String email,

        Set<TelefoneDto> telefones,

        Set<EnderecoDto> enderecos,

        Set<ContaReceberDto> contasReceber,

        @CPF(message = "CPF inválido.")
        @NotBlank(message = "CPF obrigatório.")
        String cpf,

        LocalDate dataNascimento

) implements Serializable {
}
