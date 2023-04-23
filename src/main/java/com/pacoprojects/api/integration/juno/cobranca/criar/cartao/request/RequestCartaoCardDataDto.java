package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import jakarta.validation.constraints.NotBlank;

public record RequestCartaoCardDataDto(
        @NotBlank(message = "Número do cartão deve ser informado.")
        String cardNumber,
        @NotBlank(message = "Titular do cartão deve ser informado.")
        String holderName,
        @NotBlank(message = "Código de segurança do cartão deve ser informado.")
        String securityCode,
        @NotBlank(message = "Mês de expiração deve ser informado.")
        String expirationMonth,
        @NotBlank(message = "Ano de expiração deve ser informado.")
        String expirationYear) {
}
