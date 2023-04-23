package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response;

import com.pacoprojects.api.integration.juno.error.ResponseErrorDetailsDto;

import java.util.List;

public record ResponseJunoCriarCobrancaDto(ResponseCriarCobrancaEmbeddedDto _embedded, List<ResponseErrorDetailsDto> details) {
}
