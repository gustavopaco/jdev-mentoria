package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

@Builder
public record RequestInserirFreteCarrinhoFromToDto(

        String name,

        String phone,

        String email,

        // CPF, somente para pessoas fisicas
        String document,

        // CNPJ, somente para empresas
        String company_document,

        // Inscricao Estadual, somente para empresas
        String state_register,

        String address,

        String complement,

        String number,

        // Bairro
        String district,

        String city,

        // Estado, usar esse atributo SOMENTE quando for preencher o objeto TO
        String state_abbr,

        // Codigo do Pais, ENVIAR COMO DEFAULT "BR"
        String country_id,

        // CEP
        String postal_code,

        // Alguma observacao
        String note
) {
}
