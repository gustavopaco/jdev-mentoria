package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.consulta.frete.response.ResponseConsultaFreteCompanyDto;

public record MelhorEnvioConsultaFreteDto(Long id, String nome, String valor, ResponseConsultaFreteCompanyDto empresa) { }
