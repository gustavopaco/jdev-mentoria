package com.pacoprojects.api.integration.melhor.envio.consulta.frete.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioConsultaFreteDto(RequestConsultaFreteFromToDto from, RequestConsultaFreteFromToDto to,
                                                 List<RequestConsultaFreteProdutosDto> products) {

}


