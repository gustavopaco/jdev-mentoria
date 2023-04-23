package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPagarCobrancaCartaoBilling {
    private String email;
    private RequestPagarCobrancaCartaoAddress address;
    private boolean delayed;
}
