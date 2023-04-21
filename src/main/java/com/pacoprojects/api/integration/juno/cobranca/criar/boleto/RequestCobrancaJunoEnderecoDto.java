package com.pacoprojects.api.integration.juno.cobranca.criar.boleto;

import lombok.Builder;

@Builder
public record RequestCobrancaJunoEnderecoDto(

        String cep,

        String rua,

        String numero,

        String complemento,

        String bairro,

        String cidade,

        String estado) {
}
