package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RequestInserirFreteCarrinhoProdutosDto(String name, Integer quantity, BigDecimal unitary_value) {
}
