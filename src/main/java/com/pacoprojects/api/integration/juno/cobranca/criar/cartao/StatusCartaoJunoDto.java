package com.pacoprojects.api.integration.juno.cobranca.criar.cartao;

import lombok.Builder;

@Builder
public record StatusCartaoJunoDto(String status, String message) {
}
