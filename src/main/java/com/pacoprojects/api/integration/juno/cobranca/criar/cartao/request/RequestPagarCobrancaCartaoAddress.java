package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPagarCobrancaCartaoAddress {
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postCode;
}
