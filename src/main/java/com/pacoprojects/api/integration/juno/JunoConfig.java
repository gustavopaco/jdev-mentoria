package com.pacoprojects.api.integration.juno;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.juno")
public class JunoConfig {

    private String clienteId;
    private String secretId;
    private String xResourceToken;
    private String urlDefault;

    private String authorization() {
        return getUrlDefault() + "/authorization-server";
    }

    private String apiIntegration() {
        return getUrlDefault() + "/api-integration";
    }



    public String urlAuthentication() {
        return  authorization() + "/oauth/token";
    }

    public String urlCobranca() {
        return apiIntegration() + "/charges";
    }

    public String urlPixKeys() {
        return apiIntegration() + "/pix/keys";
    }

    public String urlCancelarCobranca(String idChrBoleto) {
        return urlCobranca() + "/" + idChrBoleto + "/cancelation";
    }

    public String urlWebHook() {
        return apiIntegration() + "/notifications/webhooks";
    }

    public String urlDeletarWebHook(String idWebHook) {
        return urlWebHook() + "/" + idWebHook;
    }

    public HttpHeaders getDefaultHeaders(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Version", "2");
        headers.set("X-Resource-Token", getXResourceToken());
        headers.setBearerAuth(bearerToken);
        return headers;
    }
}
