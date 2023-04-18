package com.pacoprojects.api.integration.melhor.envio.checkout.frete.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestMelhorEnvioCheckoutFreteDto(List<String> orders) { }
