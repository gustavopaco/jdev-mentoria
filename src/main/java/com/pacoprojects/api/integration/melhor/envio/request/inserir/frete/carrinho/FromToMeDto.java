package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

public record FromToMeDto(

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

        // Codigo do Pais, ENVIAR COMO DEFAULT "BR"
        String country_id,

        // CEP
        String postal_code,

        // Alguma observacao
        String note
) {
}
