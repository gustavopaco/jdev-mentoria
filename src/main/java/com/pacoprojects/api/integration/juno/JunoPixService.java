package com.pacoprojects.api.integration.juno;

import com.pacoprojects.api.integration.juno.criar.chave.pix.RequestJunoCriarChavePixDto;
import com.pacoprojects.api.integration.juno.criar.chave.pix.response.ResponseJunoCriarChavePixDto;
import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.repository.AccessTokenJunoRepository;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JunoPixService {

    private final JunoAccessTokenService junoAccessTokenService;
    private final JunoConfig junoConfig;
    private final ApplicationConfig applicationConfig;
    private final AccessTokenJunoRepository repositoryAccessToken;


    private HttpHeaders getHeadersGeneratePixKey(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-Version", "2");
        headers.set("X-Resource-Token", junoConfig.getXResourceToken());
        headers.setBearerAuth(token);
        headers.set("X-Idempotency-Key", UUID.randomUUID().toString());
        return headers;
    }

    public void apiGerarChavePix() {

        AccessTokenJuno accessTokenJuno = junoAccessTokenService.apiGerarNovoToken();

        HttpHeaders headers = getHeadersGeneratePixKey(accessTokenJuno.getAccess_token());

        RequestJunoCriarChavePixDto criarChavePixDto = RequestJunoCriarChavePixDto
                .builder()
                .type("RANDOM_KEY")
                .build();

        HttpEntity<RequestJunoCriarChavePixDto> requestDto = new HttpEntity<>(criarChavePixDto, headers);

        ResponseEntity<ResponseJunoCriarChavePixDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlPixKeys(), HttpMethod.POST, requestDto, ResponseJunoCriarChavePixDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().key() != null) {
            accessTokenJuno.setPixKey(response.getBody().key());
            repositoryAccessToken.save(accessTokenJuno);
            System.out.println(response.getBody());
        }
    }
}
