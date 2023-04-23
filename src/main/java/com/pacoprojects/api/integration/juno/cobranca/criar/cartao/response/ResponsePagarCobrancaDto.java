package com.pacoprojects.api.integration.juno.cobranca.criar.cartao.response;

import com.pacoprojects.api.integration.juno.error.ResponseErrorDetailsDto;

import java.util.List;

public record ResponsePagarCobrancaDto(

        String transactionId,
        long installments,
        List<ResponsePagarCobrancaPayment> payments,
        List<ResponseErrorDetailsDto> details) {
}

