package com.pacoprojects.api;

import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.ViaCepDto;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ApiConsultaCep {

    private final ApplicationConfig applicationConfig;
    private static final String URL = "https://viacep.com.br/ws/";

    public EnderecoDto consultViaCepApi(String cep) {
        cep = cep.replaceAll("-", "");
        ResponseEntity<ViaCepDto> dtoResponseEntity = applicationConfig.getRestTemplateInstance().getForEntity((URL + cep + "/json"), ViaCepDto.class);

        if (dtoResponseEntity.getStatusCode().value() == 200) {
            ViaCepDto viaCepDto = dtoResponseEntity.getBody();
            if (viaCepDto != null && !viaCepDto.erro()) {
                return EnderecoDto
                        .builder()
                        .cep(viaCepDto.cep())
                        .rua(viaCepDto.logradouro())
                        .complemento(viaCepDto.complemento())
                        .bairro(viaCepDto.bairro())
                        .cidade(viaCepDto.localidade())
                        .estado(viaCepDto.uf())
                        .build();
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao buscar cep");
    }
}
