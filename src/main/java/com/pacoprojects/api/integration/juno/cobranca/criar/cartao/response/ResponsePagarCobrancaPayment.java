package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.response;

public record ResponsePagarCobrancaPayment(
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
