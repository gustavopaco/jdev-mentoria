package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response;

public record ResponseCriarCobrancaPixDto(

        String payloadInBase64,

        String id,

        String imageInBase64) {

}
