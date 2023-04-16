package com.pacoprojects.api.integration.melhor.envio.request.cancelamento.etiqueta;

import lombok.Builder;

@Builder
public record RequestCancelamentoEtiquetaOrderDto(String description, String id, Integer reason_id) {
}
