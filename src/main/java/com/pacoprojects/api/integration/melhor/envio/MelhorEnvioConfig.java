package com.pacoprojects.api.integration.melhor.envio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "integration.melhor.envio")
public class MelhorEnvioConfig {

    private String urlDefault;

    private String token;

    private String emailUserAgent;

    private String urlRastreio;



    public String urlConsultarFrete() {
        return getUrlDefault() + "api/v2/me/shipment/calculate";
    }

    public String urlInserirFreteCarrinho() {
        return getUrlDefault() + "api/v2/me/cart";
    }

    public String urlCheckoutFrete() {
        return getUrlDefault() + "api/v2/me/shipment/checkout";
    }

    public String urlGerarEtiqueta() {
        return getUrlDefault() + "api/v2/me/shipment/generate";
    }

    public String urlImprimirEtiqueta() {
        return getUrlDefault() +  "api/v2/me/shipment/print";
    }

    public String urlListarAgencias() {
        return getUrlDefault() + "api/v2/me/shipment/agencies";
    }

    public String urlCancelamentoEtiquetas() {
        return getUrlDefault() + "api/v2/me/shipment/cancel";
    }

    public String urlRastreioPedido() {
        return getUrlDefault() + "api/v2/me/shipment/tracking";
    }

}
