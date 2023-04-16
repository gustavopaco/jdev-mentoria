package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutosMeDto(String name, Integer quantity, BigDecimal unitary_value) {
}
