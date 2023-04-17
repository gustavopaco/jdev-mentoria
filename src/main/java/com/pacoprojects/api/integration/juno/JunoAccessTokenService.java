package com.pacoprojects.api.integration.juno;

import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.repository.AccessTokenJunoRepository;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.ssl.HostIgnoreClient;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JunoAccessTokenService {

    private final AccessTokenJunoRepository repositoryAccessTokenJuno;
    private final ApplicationConfig applicationConfig;
    private final JunoConfig junoConfig;

    private AccessTokenJuno buscarTokenAtivo() {
        Optional<AccessTokenJuno> accessTokenJuno = repositoryAccessTokenJuno.findAll().stream().findFirst();

        if (accessTokenJuno.isPresent() && !accessTokenJuno.get().isTokenExpired()) {
            return accessTokenJuno.get();
        }
        return null;

    }

    private String getTokenAutenticacao() {
        String basicChave = junoConfig.getClienteId() + ":" + junoConfig.getSecretId();
        return Base64.getEncoder().encodeToString(basicChave.getBytes());
    }

    private HttpHeaders getHeadersConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(getTokenAutenticacao());
        return headers;
    }

    public AccessTokenJuno apiGerarNovoToken() {

        if (buscarTokenAtivo() == null) {

            HttpHeaders headers = getHeadersConfiguration();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<AccessTokenJuno> responseEntity = applicationConfig
                    .getRestTemplateInstance()
                    .exchange(junoConfig.getUrlDefault(), HttpMethod.POST, requestEntity, AccessTokenJuno.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
                repositoryAccessTokenJuno.deleteAll();

                AccessTokenJuno accessTokenJuno = responseEntity.getBody();
                accessTokenJuno.setToken_autenticacao(getTokenAutenticacao());

                return repositoryAccessTokenJuno.save(accessTokenJuno);
            } else {
                return null;
            }
        }
        return buscarTokenAtivo();
    }

    private AccessTokenJuno gerarNovoTokenAlex() throws NoSuchAlgorithmException, KeyManagementException {

        if (buscarTokenAtivo() == null) {

            String clienteID = "vi7QZerW09C8JG1o";
            String secretID = "$A_+&ksH}&+2<3VM]1MZqc,F_xif_-Dc";
            String urlApiJuno = "https://api.juno.com.br/";
            String auth = "authorization-server/oauth/token?grant_type=client_credentials";

            Client client = new HostIgnoreClient(urlApiJuno).hosIgnoringCLient();

            WebResource webResource = client.resource(urlApiJuno + auth);

            String basicChave = junoConfig.getClienteId() + ":" + junoConfig.getSecretId();

            String token_autenticacao = Base64.getEncoder().encodeToString(basicChave.getBytes());

//            ClientResponse clientResponse = webResource
//                    .accept(MediaType.APPLICATION_FORM_URLENCODED)
//                    .header("Content-Type", "application/x-www-form-urlencoded")
//                    .header(HttpHeaders.AUTHORIZATION, "Basic " + token_autenticacao)
//                    .post(ClientResponse.class);
//
//            if (clientResponse.getStatus() == 200) {
//                repositoryAccessTokenJuno.deleteAll();
//
//                AccessTokenJuno accessTokenJuno = clientResponse.getEntity(AccessTokenJuno.class);
//                accessTokenJuno.setAccess_token(token_autenticacao);
//
//                return repositoryAccessTokenJuno.save(accessTokenJuno);
//            } else {
//                return null;
//            }

        }
        return buscarTokenAtivo();
    }
}
