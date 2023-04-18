package com.pacoprojects.api.integration.melhor.envio.consulta.frete.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestConsultaFreteProdutosDto(String id, Integer width, Integer height, Integer length, BigDecimal weight,
                                              BigDecimal insurance_value, Integer quantity) {

}
