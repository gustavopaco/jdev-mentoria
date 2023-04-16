package com.pacoprojects.api.integration.melhor.envio.request.checkout.frete;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioCheckoutFreteDto(List<String> orders) { }
