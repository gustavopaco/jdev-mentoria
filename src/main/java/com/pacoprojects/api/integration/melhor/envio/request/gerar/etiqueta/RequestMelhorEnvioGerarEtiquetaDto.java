package com.pacoprojects.api.integration.melhor.envio.request.gerar.etiqueta;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioGerarEtiquetaDto(List<String> orders) { }