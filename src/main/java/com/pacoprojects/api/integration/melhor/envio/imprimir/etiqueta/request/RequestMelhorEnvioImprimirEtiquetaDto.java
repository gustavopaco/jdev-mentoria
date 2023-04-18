//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType

package com.pacoprojects.api.integration.melhor.envio.imprimir.etiqueta.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioImprimirEtiquetaDto(String mode, List<String> orders) { }
