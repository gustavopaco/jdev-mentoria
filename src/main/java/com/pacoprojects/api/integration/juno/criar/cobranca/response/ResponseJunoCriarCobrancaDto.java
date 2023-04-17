package com.pacoprojects.api.integration.juno.criar.cobranca.response;

import java.util.List;

public record ResponseJunoCriarCobrancaDto(ResponseCriarCobrancaEmbeddedDto _embedded, List<ResponseCriarCobrancaLinkDto> _links) {
}
