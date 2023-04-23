package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record RequestCartaoDto(

        @NotNull(message = "Id da Venda deve ser informado.")
        Long idVenda,
        @NotBlank(message = "Nome do comprador deve ser informado.")
        String nome,
        @NotBlank(message = "E-mail do comprador deve ser informado.")
        String email,
        @CPF(message = "CPF inválido.")
        @NotBlank(message = "CPF deve ser informado.")
        String cpf,
        @NotNull(message = "Número de parcelas deve ser informado.")
        @Range(min = 1, max = 12, message = "Número de parcelas deve ser entre 1 e 12")
        Integer parcelas,
        @NotNull(message = "Valor total deve ser informado")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        @Min(value = 1, message = "Valor total deve ser no minimo de R$1.00")
        BigDecimal valorTotal,
        @Valid
        RequestCartaoCardDataDto cardData,
        @NotBlank(message = "O Hash do cartão deve ser informado.")
        String cardHash,
        @Valid
        RequestCartaoAddressDto address
) {
}

