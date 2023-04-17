package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;

@Builder
// Endereco do comprador
public record RequestCriarCobrancaAddressDto(String number, String city, String street, String postCode,
                                             String neighborhood, String state,
                                             String complement) {
}
