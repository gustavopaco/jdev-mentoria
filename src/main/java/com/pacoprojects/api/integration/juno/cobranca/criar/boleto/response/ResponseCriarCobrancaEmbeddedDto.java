package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response;

import java.util.List;

public record ResponseCriarCobrancaEmbeddedDto(List<ResponseCriarCobrancaChargeDto> charges) {
}
