package com.pacoprojects.api.integration.melhor.envio.response.consulta.frete;

import java.util.List;

public record PackagesMeDto(String price, String discount, String format, String weight, String insurance_value,
                            DimensionsMeDto dimensions, List<ProdutosMeDto> products) {
}
