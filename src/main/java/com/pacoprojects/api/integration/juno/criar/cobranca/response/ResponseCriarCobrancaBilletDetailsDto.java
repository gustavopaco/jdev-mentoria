package com.pacoprojects.api.integration.juno.criar.cobranca.response;

public record ResponseCriarCobrancaBilletDetailsDto(
        String bankAccount,

        String barcodeNumber,

        String portfolio,

        String ourNumber) {
}
