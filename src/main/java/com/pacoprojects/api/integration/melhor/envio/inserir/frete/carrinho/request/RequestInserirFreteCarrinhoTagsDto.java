package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

@Builder
public record RequestInserirFreteCarrinhoTagsDto(String tag, String url) {
}
