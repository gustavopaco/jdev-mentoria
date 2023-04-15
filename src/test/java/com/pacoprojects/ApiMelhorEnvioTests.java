package com.pacoprojects;

import com.pacoprojects.api.integration.ApiIntegracao;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.FromToRequest;
import com.pacoprojects.api.integration.melhor.envio.request.MelhorEnvioDtoRequest;
import com.pacoprojects.api.integration.melhor.envio.request.ProdutoRequest;
import com.pacoprojects.api.integration.melhor.envio.response.MelhorEnvioDtoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiMelhorEnvioTests {
    private final RestTemplate restTemplate;

    private final MelhorEnvioMapper mapperMelhorEnvio;

    @Autowired
    public ApiMelhorEnvioTests(RestTemplate restTemplate, MelhorEnvioMapper mapperMelhorEnvio) {
        this.restTemplate = restTemplate;
        this.mapperMelhorEnvio = mapperMelhorEnvio;
    }

    @Test
    void ConexaoApi() {

        HttpHeaders headers = getHeaderConfiguration();

        String urlCalculoFrete = ApiIntegracao.URL_SANDBOX_MELHOR_ENVIO + "/api/v2/me/shipment/calculate";

        MelhorEnvioDtoRequest melhorEnvioDtoRequest = MelhorEnvioDtoRequest
                .builder()
                .from(new FromToRequest("30590-253"))
                .to(new FromToRequest("65068-097"))
                .products(List.of(
                        new ProdutoRequest("x", 11, 17, 11, 0.3, new BigDecimal("10.1"), 1)
                ))
                .build();

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<MelhorEnvioDtoRequest> entity = new HttpEntity<>(melhorEnvioDtoRequest, headers);

        // Classe responsavel por definir a Response como List<MelhorEnvioDtoResponse>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<MelhorEnvioDtoResponse>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<MelhorEnvioDtoResponse>> response = restTemplate.exchange(urlCalculoFrete, HttpMethod.POST, entity, responseType);
        List<MelhorEnvioDtoResponse> body = response.getBody();
//        System.out.println(formatResponse(body));
    }

    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ApiIntegracao.TOKEN_SANDBOX_MELHOR_ENVIO);
        headers.set("User-Agent", "gustavopaco@gmail.com");
        return headers;
    }

    private List<MelhorEnvioDto> formatResponse(List<MelhorEnvioDtoResponse> responseBody) {
        List<MelhorEnvioDto> melhorEnvioDtos = new ArrayList<>();
        if (responseBody != null) {
            melhorEnvioDtos = responseBody.stream().map(melhorEnvioDtoResponse -> {
                if (melhorEnvioDtoResponse.error() == null) {
                    return mapperMelhorEnvio.toEntity(melhorEnvioDtoResponse);
                }
                return null;
            }).filter(Objects::nonNull).toList();
        }
        return melhorEnvioDtos;
    }
}
