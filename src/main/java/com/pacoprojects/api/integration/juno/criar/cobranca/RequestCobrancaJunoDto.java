package com.pacoprojects.api.integration.juno.criar.cobranca;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

// Dados que irao vir da tela para ser enviado para Api
@Builder
public record RequestCobrancaJunoDto(

        @NotNull(message = "Código da venda deve ser informado.")
        Long idVenda,

        @NotBlank(message = "Descrição do boleto deve ser informado.")
        String description,

        String payerName,

        String payerPhone,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal amount,

        @NotNull(message = "Número de parcelas deve ser informado.")
        Long installMents,

        String reference,

        Integer recurrency,

        String payerCpfCnpj,

        String email,

        RequestCobrancaJunoEnderecoDto address) {
}
