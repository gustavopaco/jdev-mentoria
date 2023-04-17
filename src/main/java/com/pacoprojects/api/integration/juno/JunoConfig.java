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
    private String urlDefault;
}
