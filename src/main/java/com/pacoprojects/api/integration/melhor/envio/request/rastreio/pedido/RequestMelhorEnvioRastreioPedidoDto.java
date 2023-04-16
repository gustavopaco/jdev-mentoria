package com.pacoprojects.api.integration.melhor.envio.request.rastreio.pedido;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioRastreioPedidoDto(List<String> orders) {
}
