package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CNPJ;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.pacoprojects.model.PessoaJuridica} entity
 */
@Builder
public record PessoaJuridicaDto(

        Long id,

        @NotBlank(message = "Nome obrigatório.")
        String nome,

        @NotBlank(message = "E-mail obrigatório.")
        String email,

        Set<TelefoneDto> telefones,

        Set<EnderecoDto> enderecos,

        Set<ContaReceberDto> contasReceber,

        @CNPJ(message = "CNPJ obrigatório.")
        @NotBlank(message = "CNPJ obrigatório.")
        String cnpj,

        @NotBlank(message = "Inscrição Estadual obrigatório.")
        String inscricaoEstadual,

        String inscricaoMunicipal,

        @NotBlank(message = "Nome Fantasia obrigatório.")
        String nomeFantasia,

        @NotBlank(message = "Razão Social obrigatório.")
        String razaoSocial,

        String categoria

) implements Serializable {
}
