package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestInserirFreteCarrinhoProdutosDto(String name, Integer quantity, BigDecimal unitary_value) {
}
