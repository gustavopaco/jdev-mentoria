package com.pacoprojects.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String prefix;
    private String secretKey;
    private Integer daysUntilExpiration;
}
