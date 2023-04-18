package com.pacoprojects.api.integration.juno;

import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.repository.AccessTokenJunoRepository;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JunoAccessTokenService {

    private final AccessTokenJunoRepository repositoryAccessTokenJuno;
    private final ApplicationConfig applicationConfig;
    private final JunoConfig junoConfig;

    private AccessTokenJuno buscarTokenAtivo() {
        return repositoryAccessTokenJuno.findAll().stream().findFirst().orElse(null);
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
        AccessTokenJuno tokenAtivo = buscarTokenAtivo();

        if (tokenAtivo != null && !tokenAtivo.isTokenExpired()) {
            return tokenAtivo;
        }

            HttpHeaders headers = getHeadersConfiguration();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<AccessTokenJuno> responseEntity = applicationConfig
                    .getRestTemplateInstance()
                    .exchange(junoConfig.urlAuthentication(), HttpMethod.POST, requestEntity, AccessTokenJuno.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {

                AccessTokenJuno accessTokenResponse = responseEntity.getBody();
                accessTokenResponse.setToken_autenticacao(getTokenAutenticacao());

                if (tokenAtivo != null) {
                    BeanUtils.copyProperties(accessTokenResponse, tokenAtivo, "id", "pixKey");
                    return repositoryAccessTokenJuno.save(tokenAtivo);
                }
                return repositoryAccessTokenJuno.save(accessTokenResponse);
            }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na Api Juno para geração de Token");
    }
}


//    private AccessTokenJuno gerarNovoTokenAlex() throws NoSuchAlgorithmException, KeyManagementException {
//
//        if (buscarTokenAtivo() == null) {
//
//            String clienteID = "vi7QZerW09C8JG1o";
//            String secretID = "$A_+&ksH}&+2<3VM]1MZqc,F_xif_-Dc";
//            String urlApiJuno = "https://api.juno.com.br/";
//            String auth = "authorization-server/oauth/token?grant_type=client_credentials";
//
//            Client client = new HostIgnoreClient(urlApiJuno).hosIgnoringCLient();
//
//            WebResource webResource = client.resource(urlApiJuno + auth);
//
//            String basicChave = junoConfig.getClienteId() + ":" + junoConfig.getSecretId();
//
//            String token_autenticacao = Base64.getEncoder().encodeToString(basicChave.getBytes());

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

//        }
//        return buscarTokenAtivo();
//    }
