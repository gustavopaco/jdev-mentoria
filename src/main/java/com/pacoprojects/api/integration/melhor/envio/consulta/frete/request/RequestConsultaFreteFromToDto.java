package com.pacoprojects.api.integration.melhor.envio.consulta.frete.request;

import lombok.Builder;

@Builder
public record RequestConsultaFreteFromToDto(String postal_code) {
}
