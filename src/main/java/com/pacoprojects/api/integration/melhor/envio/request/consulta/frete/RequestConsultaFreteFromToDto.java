package com.pacoprojects.api.integration.melhor.envio.request.consulta.frete;

import lombok.Builder;

@Builder
public record RequestConsultaFreteFromToDto(String postal_code) {
}
