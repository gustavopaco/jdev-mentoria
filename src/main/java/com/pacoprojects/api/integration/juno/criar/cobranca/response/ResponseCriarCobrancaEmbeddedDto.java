package com.pacoprojects.api.integration.juno.criar.cobranca.response;

import java.util.List;

public record ResponseCriarCobrancaEmbeddedDto(List<ResponseCriarCobrancaChargeDto> charges) {
}
