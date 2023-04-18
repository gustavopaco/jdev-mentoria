package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestInserirFreteCarrinhoVolumesDto(Integer height, Integer width, Integer length, BigDecimal weight) {
}
