package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestJunoPagarCobrancaCartao {
    private String chargeId;
    private RequestPagarCobrancaCartaoBilling billing;
    private RequestPagarCobrancaCartaoDetailsDto creditCardDetails;
}

