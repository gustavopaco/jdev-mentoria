package com.pacoprojects.api;

import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.MelhorEnvioConsultaFreteRequestDto;
import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.MelhorEnvioConsultaFreteResponseDto;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiConsultaMelhorEnvio {

    private final ApplicationConfig applicationConfig;
    private final MelhorEnvioMapper mapperMelhorEnvio;

    public List<MelhorEnvioConsultaFreteDto> consultMelhorEnvioFrete(MelhorEnvioConsultaFreteRequestDto melhorEnvioConsultaFreteRequestDto) {

        HttpHeaders headers = getHeaderConfiguration();

        // Classe que junta o BODY que sera enviado como POSTRequest com o Header para ser enviado ao MelhorEnvio
        HttpEntity<MelhorEnvioConsultaFreteRequestDto> bodyAndHeaders = new HttpEntity<>(melhorEnvioConsultaFreteRequestDto, headers);

        // Classe responsavel por definir que a Response sera como List<MelhorEnvioConsultaFreteResponseDto>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<MelhorEnvioConsultaFreteResponseDto>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<MelhorEnvioConsultaFreteResponseDto>> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_CONSULTAR_FRETE, HttpMethod.POST, bodyAndHeaders, responseType);

        if (response.getStatusCode().value() != 200 || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "MelhorEnvioException");
        }

        return getDtoMapped(response.getBody());
    }

    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ApiConstantes.TOKEN_SANDBOX_MELHOR_ENVIO);
        headers.set("User-Agent", "gustavopaco@gmail.com");
        return headers;
    }

    private List<MelhorEnvioConsultaFreteDto> getDtoMapped(List<MelhorEnvioConsultaFreteResponseDto> responseBody) {

        return responseBody.stream().map(melhorEnvioConsultaFreteResponseDto -> {
            if (melhorEnvioConsultaFreteResponseDto.error() == null) {
                return mapperMelhorEnvio.toEntity(melhorEnvioConsultaFreteResponseDto);
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }
}
