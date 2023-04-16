package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import lombok.Builder;

@Builder
public record RequestInserirFreteCarrinhoTagsDto(String tag, String url) {
}
