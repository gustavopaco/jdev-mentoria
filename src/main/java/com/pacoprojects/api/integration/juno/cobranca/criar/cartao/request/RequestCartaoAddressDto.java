package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import jakarta.validation.constraints.NotBlank;

public record RequestCartaoAddressDto(
        @NotBlank(message = "Rua deve ser informado.")
        String street,
        @NotBlank(message = "Número do endereço deve ser informado.")
        String number,
        @NotBlank(message = "Bairro deve ser informado.")
        String neighborhood,
        @NotBlank(message = "Cidade deve ser informado.")
        String city,
        @NotBlank(message = "Estado deve ser informado.")
        String state,
        @NotBlank(message = "CEP deve ser informado.")
        String postCode) {
}
