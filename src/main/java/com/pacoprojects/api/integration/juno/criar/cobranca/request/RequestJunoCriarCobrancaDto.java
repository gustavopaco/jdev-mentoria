package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;

@Builder
public record RequestJunoCriarCobrancaDto(
        RequestCriarCobrancaChargeDto charge,

        RequestCobrancaBillingDto billing) {
}


