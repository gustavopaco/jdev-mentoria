package com.pacoprojects.api.integration.melhor.envio.response.consulta.frete;

import java.util.List;

public record ResponseConsultaFretePackagesDto(String price, String discount, String format, String weight, String insurance_value,
                                               ResponseConsultaFreteDimensionsDto dimensions, List<ResponseConsultaFreteProdutosDto> products) {
}
