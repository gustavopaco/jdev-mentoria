package com.pacoprojects.dto;

import com.pacoprojects.model.PessoaJuridica;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.PessoaFisica} entity
 */
@Builder
public record RegisterPessoaFisicaDto(

        Long id,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "E-mail obrigatório.")
        String email,

        Set<TelefoneDto> telefones,

        Set<EnderecoDto> enderecos,

        @CPF(message = "CPF inválido.")
        @NotBlank(message = "CPF obrigatório.")
        String cpf,

        LocalDate dataNascimento,

        PessoaJuridica empresa

) implements Serializable {
}
