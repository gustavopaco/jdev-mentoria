package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPagarCobrancaCartaoDetailsDto {
    private String creditCardHash;

}
