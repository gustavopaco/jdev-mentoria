package com.pacoprojects.api;

import com.pacoprojects.dto.ConsultaReceitaAwsDto;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ApiConsultaCnpj {

    private final ApplicationConfig applicationConfig;

    private static final String URL = "https://receitaws.com.br/v1/cnpj/";

    public ConsultaReceitaAwsDto consultReceitaAwsApi(String cnpj) {
        cnpj = cnpj.replaceAll("\\.", "").replaceAll("/", "").replaceAll("-", "");

        ResponseEntity<ConsultaReceitaAwsDto> dtoResponseEntity = applicationConfig.getRestTemplateInstance().getForEntity((URL + cnpj), ConsultaReceitaAwsDto.class);

        return switch (dtoResponseEntity.getStatusCode().value()) {
            case 200 -> dtoResponseEntity.getBody();

            case 429 -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Muitas requisicoes, so podemos fazer 3 consultas por minuto, tente nvoamente daqui a pouco.");

            case 504 -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O servidor demorou muito para responder, tente mais tarde ou entre em contato com o administrador.");

            default -> throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Serviço fora do ar.");
        };
    }
}
