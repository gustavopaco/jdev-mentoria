package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record RequestInserirFreteCarrinhoOptionsDto(
        BigDecimal insurance_value,
        Boolean receipt,
        Boolean own_hand,
        Boolean reverse,
        Boolean non_commercial,
        RequestInserirFreteCarrinhoInvoiceDto invoice,
        String platform,
        List<RequestInserirFreteCarrinhoTagsDto> tags

) {
}
