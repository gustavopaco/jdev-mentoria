package com.pacoprojects.api.integration.melhor.envio.gerar.etiqueta.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioGerarEtiquetaDto(List<String> orders) { }
