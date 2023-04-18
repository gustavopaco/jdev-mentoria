package com.pacoprojects.api.integration.juno.criar.cobranca.response;

import java.util.List;

public record ResponseCriarCobrancaChargeDto(

        String id,

        long code,

        String reference,

        String dueDate,

        String link,

        String checkoutUrl,

        String installmentLink,

        String payNumber,

        long amount,

        String status,

        ResponseCriarCobrancaBilletDetailsDto billetDetails,

        List<ResponseCriarCobrancaPaymentDto> payments,

        ResponseCriarCobrancaPixDto pix,

        ResponseCriarCobrancaLinkDto _links) {
}
