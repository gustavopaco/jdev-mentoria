package com.pacoprojects.api.integration.melhor.envio.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoRequest(String id, Integer width, Integer height, Integer length, Double weight, BigDecimal insurance_value, Integer quantity) { }
