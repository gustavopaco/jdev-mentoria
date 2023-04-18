package com.pacoprojects.api.integration.melhor.envio.rastreio.pedido.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioRastreioPedidoDto(List<String> orders) {
}
