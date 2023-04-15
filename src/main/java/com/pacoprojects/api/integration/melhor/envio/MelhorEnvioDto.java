package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.response.CompanyResponse;

public record MelhorEnvioDto(Long id, String nome, String valor, CompanyResponse empresa) { }
