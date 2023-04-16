package com.pacoprojects.api.integration.melhor.envio.request.consulta.frete;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioConsultaFreteDto(RequestConsultaFreteFromToDto from, RequestConsultaFreteFromToDto to,
                                                 List<RequestConsultaFreteProdutosDto> products) {

}


