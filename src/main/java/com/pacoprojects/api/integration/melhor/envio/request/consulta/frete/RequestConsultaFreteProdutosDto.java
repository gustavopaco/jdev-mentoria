package com.pacoprojects.api.integration.melhor.envio.request.consulta.frete;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestConsultaFreteProdutosDto(String id, Integer width, Integer height, Integer length, BigDecimal weight,
                                              BigDecimal insurance_value, Integer quantity) {

}
