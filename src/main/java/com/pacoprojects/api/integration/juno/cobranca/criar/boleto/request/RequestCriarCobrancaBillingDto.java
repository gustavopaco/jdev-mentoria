package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request;

import lombok.Builder;
import lombok.Data;

// Note: Dados do comprador
@Data
@Builder
public class RequestCriarCobrancaBillingDto {
    // OBRIGATORIO -  Nome
    private String name;
    // OBRIGATORIO - Endereço
    private RequestCriarCobrancaAddressDto address;
    private String secondaryEmail;
    private String phone;
    // OBRIGATORIO - CPF ou CNPJ. Envie sem ponto ou traço.
    private String document;
    private String birthDate;
    private String email;
    private boolean notify = true;

}
