package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestJunoCriarCobrancaDto {
    private RequestCriarCobrancaChargeDto charge;
    private RequestCriarCobrancaBillingDto billing;
}


