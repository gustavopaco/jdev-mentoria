package com.pacoprojects.api.integration.juno;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
}
