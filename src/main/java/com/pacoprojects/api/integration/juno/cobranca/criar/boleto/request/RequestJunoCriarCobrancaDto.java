package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestJunoCriarCobrancaDto {
    private RequestCriarCobrancaChargeDto charge;
    private RequestCriarCobrancaBillingDto billing;
}


