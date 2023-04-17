package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
// Dados do comprador
public class RequestCobrancaBillingDto {
    private RequestCriarCobrancaAddressDto address;
    private String secondaryEmail;
    private String phone;
    private String document;
    private String name;
    private String birthDate;
    private String email;
    private boolean notify = true;

}
