package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;
import lombok.Data;

// Endereco do comprador
@Builder
@Data
public class RequestCriarCobrancaAddressDto {
    private String number;
    private String city;
    private String street;
    private String postCode;
    private String neighborhood;
    private String state;
    private String complement;
}
