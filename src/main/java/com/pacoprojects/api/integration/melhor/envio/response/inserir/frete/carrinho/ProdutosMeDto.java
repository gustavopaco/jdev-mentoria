package com.pacoprojects.api.integration.melhor.envio.response.inserir.frete.carrinho;

import java.math.BigDecimal;

public record ProdutosMeDto(String name, Integer quantity, BigDecimal unitary_value, BigDecimal weight) {
}
