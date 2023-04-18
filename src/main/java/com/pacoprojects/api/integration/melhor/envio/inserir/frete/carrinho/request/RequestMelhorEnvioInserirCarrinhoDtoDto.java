package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioInserirCarrinhoDtoDto(Integer service, Integer agency,
                                                      RequestInserirFreteCarrinhoFromToDto from,
                                                      RequestInserirFreteCarrinhoFromToDto to,
                                                      List<RequestInserirFreteCarrinhoProdutosDto> products,
                                                      List<RequestInserirFreteCarrinhoVolumesDto> volumes,
                                                      RequestInserirFreteCarrinhoOptionsDto options) {
}


