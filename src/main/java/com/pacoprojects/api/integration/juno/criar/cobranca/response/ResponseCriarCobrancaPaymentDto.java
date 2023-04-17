package com.pacoprojects.api.integration.juno.criar.cobranca.response;

public record ResponseCriarCobrancaPaymentDto(

        String id,

        String chargeId,

        String date,

        String releaseDate,

        long amount,

        long fee,

        String type,

        String status,

        String transactionId,

        String failReason) {
}
