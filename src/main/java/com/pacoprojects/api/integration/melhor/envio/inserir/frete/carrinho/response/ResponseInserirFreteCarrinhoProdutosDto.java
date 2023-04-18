package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.response;

import java.math.BigDecimal;

public record ResponseInserirFreteCarrinhoProdutosDto(String name, Integer quantity, BigDecimal unitary_value, BigDecimal weight) {
}
