package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;

@Builder
public record RequestCriarCobrancaSplitDto(

        String recipientToken,

        long amount,

        boolean chargeFee,

        boolean amountRemainder,

        long percentage) {
}
