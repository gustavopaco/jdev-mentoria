package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response;

public record ResponseCriarCobrancaBilletDetailsDto(
        String bankAccount,

        String barcodeNumber,

        String portfolio,

        String ourNumber) {
}
