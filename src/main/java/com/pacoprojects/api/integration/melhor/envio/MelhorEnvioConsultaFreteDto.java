package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.CompanyMeDto;

public record MelhorEnvioConsultaFreteDto(Long id, String nome, String valor, CompanyMeDto empresa) { }
