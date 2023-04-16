package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import java.math.BigDecimal;
import java.util.List;

public record OptionsMeDto(
        BigDecimal insurance_value,
        Boolean receipt,
        Boolean own_hand,
        Boolean reverse,
        Boolean non_commercial,
        InvoiceMeDto invoice,
        String platform,
        List<TagsMeDto> tags

) {
}
