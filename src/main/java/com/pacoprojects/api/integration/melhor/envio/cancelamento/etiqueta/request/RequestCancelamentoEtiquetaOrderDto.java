package com.pacoprojects.api.integration.melhor.envio.cancelamento.etiqueta.request;

import lombok.Builder;

@Builder
public record RequestCancelamentoEtiquetaOrderDto(String description, String id, Integer reason_id) {
}
