package com.pacoprojects.api.integration.melhor.envio.request.consulta.frete;

import lombok.Builder;

import java.util.List;

@Builder
public record MelhorEnvioConsultaFreteRequestDto(FromToMeDto from, FromToMeDto to,
                                                 List<ProdutosMeDto> products) {

}


