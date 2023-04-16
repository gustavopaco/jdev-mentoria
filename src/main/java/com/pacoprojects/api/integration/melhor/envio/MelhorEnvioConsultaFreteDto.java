package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.ResponseConsultaFreteCompanyDto;

public record MelhorEnvioConsultaFreteDto(Long id, String nome, String valor, ResponseConsultaFreteCompanyDto empresa) { }
