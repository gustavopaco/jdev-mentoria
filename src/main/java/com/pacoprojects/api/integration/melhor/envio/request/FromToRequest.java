package com.pacoprojects.api.integration.melhor.envio.request;

import lombok.Builder;

@Builder
public record FromToRequest(String postal_code) {
}
