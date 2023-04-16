package com.pacoprojects.api.integration.melhor.envio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.melhor.envio")
public class MelhorEnvioConfig {

    private String url;

    private String token;

    private String emailUserAgent;


    public String urlConsultarFrete() {
        return getUrl() + "api/v2/me/shipment/calculate";
    }

    public String urlInserirFreteCarrinho() {
        return getUrl() + "api/v2/me/cart";
    }

    public String urlCheckoutFrete() {
        return getUrl() + "api/v2/me/shipment/checkout";
    }

    public String urlGerarEtiqueta() {
        return getUrl() + "api/v2/me/shipment/generate";
    }

    public String urlImprimirEtiqueta() {
        return getUrl() +  "api/v2/me/shipment/print";
    }
}
