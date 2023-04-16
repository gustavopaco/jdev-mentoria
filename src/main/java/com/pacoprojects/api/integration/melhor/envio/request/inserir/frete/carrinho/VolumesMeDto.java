package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record VolumesMeDto(Integer height, Integer width, Integer length, BigDecimal weight) {
}
