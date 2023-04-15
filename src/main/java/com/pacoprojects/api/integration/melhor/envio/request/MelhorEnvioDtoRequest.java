package com.pacoprojects.api.integration.melhor.envio.request;

import lombok.Builder;

import java.util.List;

@Builder
public record MelhorEnvioDtoRequest(FromToRequest from, FromToRequest to, List<ProdutoRequest> products) { }

