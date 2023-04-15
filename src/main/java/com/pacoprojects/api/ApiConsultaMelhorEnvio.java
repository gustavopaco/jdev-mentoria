package com.pacoprojects.api;

import com.pacoprojects.api.integration.ApiIntegracao;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.MelhorEnvioDtoRequest;
import com.pacoprojects.api.integration.melhor.envio.response.MelhorEnvioDtoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiConsultaMelhorEnvio {

    private final RestTemplate restTemplate;
    private final MelhorEnvioMapper mapperMelhorEnvio;

    public List<MelhorEnvioDto> consultMelhorEnvioFrete(MelhorEnvioDtoRequest melhorEnvioDtoRequest) {


        HttpHeaders headers = getHeaderConfiguration();

        // Classe que junta o BODY que sera enviado como POSTRequest com o Header para ser enviado ao MelhorEnvio
        HttpEntity<MelhorEnvioDtoRequest> bodyAndHeaders = new HttpEntity<>(melhorEnvioDtoRequest, headers);

        // Classe responsavel por definir que a Response sera como List<MelhorEnvioDtoResponse>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<MelhorEnvioDtoResponse>> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<MelhorEnvioDtoResponse>> response = restTemplate
                .exchange(ApiIntegracao.URL_SANDBOX_MELHOR_ENVIO_CONSULTAR_FRETE, HttpMethod.POST, bodyAndHeaders, responseType);

        if (response.getStatusCode().value() != 200 || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "MelhorEnvioException");
        }

        return getDtoMapped(response.getBody());
    }

    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ApiIntegracao.TOKEN_SANDBOX_MELHOR_ENVIO);
        headers.set("User-Agent", "gustavopaco@gmail.com");
        return headers;
    }

    private List<MelhorEnvioDto> getDtoMapped(List<MelhorEnvioDtoResponse> responseBody) {

        return responseBody.stream().map(melhorEnvioDtoResponse -> {
            if (melhorEnvioDtoResponse.error() == null) {
                return mapperMelhorEnvio.toEntity(melhorEnvioDtoResponse);
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }
}
