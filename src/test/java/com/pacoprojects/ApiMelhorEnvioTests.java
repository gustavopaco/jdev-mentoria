package com.pacoprojects;

import com.pacoprojects.api.ApiConstantes;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.FromToMeDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.MelhorEnvioConsultaFreteRequestDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.ProdutosMeDto;
import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.MelhorEnvioConsultaFreteResponseDto;
import com.pacoprojects.api.integration.melhor.envio.response.inserir.frete.carrinho.MelhorEnvioInserirFreteCarrinhoResponseDto;
import com.pacoprojects.security.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiMelhorEnvioTests {
    private final ApplicationConfig applicationConfig;

    private final MelhorEnvioMapper mapperMelhorEnvio;

    @Autowired
    public ApiMelhorEnvioTests(ApplicationConfig applicationConfig, MelhorEnvioMapper mapperMelhorEnvio) {
        this.applicationConfig = applicationConfig;
        this.mapperMelhorEnvio = mapperMelhorEnvio;
    }

    @Test
    void ConsultaFreteMelhorEnvio() {

        HttpHeaders headers = getHeaderConfiguration();

        MelhorEnvioConsultaFreteRequestDto melhorEnvioConsultaFreteRequestDto = MelhorEnvioConsultaFreteRequestDto
                .builder()
                .from(new FromToMeDto("30590-253"))
                .to(new FromToMeDto("65068-097"))
                .products(List.of(
                        new ProdutosMeDto("x", 11, 17, 11, new BigDecimal("0.3"), new BigDecimal("10.1"), 1)
                ))
                .build();

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<MelhorEnvioConsultaFreteRequestDto> entity = new HttpEntity<>(melhorEnvioConsultaFreteRequestDto, headers);

        // Classe responsavel por definir a Response como List<MelhorEnvioConsultaFreteResponseDto>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<MelhorEnvioConsultaFreteResponseDto>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<MelhorEnvioConsultaFreteResponseDto>> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_CONSULTAR_FRETE, HttpMethod.POST, entity, responseType);

        List<MelhorEnvioConsultaFreteResponseDto> body = response.getBody();
        System.out.println(formatResponse(body));
    }

    @Test
        // Insere o frete no carrinho
    void inserirFreteCarrinhoMelhorEnvio() {
        HttpHeaders headers = getHeaderConfiguration();

        String json = "{    \"service\": 3,    \"agency\": 49,    \"from\": {        \"name\": \"Nome do remetente\",        \"phone\": \"53984470102\",        \"email\": \"contato@melhorenvio.com.br\",        \"document\": \"16571478358\",        \"company_document\": \"89794131000100\",        \"state_register\": \"123456\",        \"address\": \"Endereço do remetente\",        \"complement\": \"Complemento\",        \"number\": \"1\",        \"district\": \"Bairro\",        \"city\": \"São Paulo\",        \"country_id\": \"BR\",        \"postal_code\": \"01002001\",        \"note\": \"observação\"    },    \"to\": {        \"name\": \"Nome do destinatário\",        \"phone\": \"53984470102\",        \"email\": \"contato@melhorenvio.com.br\",        \"document\": \"25404918047\",        \"company_document\": \"07595604000177\",        \"state_register\": \"123456\",        \"address\": \"Endereço do destinatário\",        \"complement\": \"Complemento\",        \"number\": \"2\",        \"district\": \"Bairro\",        \"city\": \"Porto Alegre\",        \"state_abbr\": \"RS\",        \"country_id\": \"BR\",        \"postal_code\": \"90570020\",        \"note\": \"observação\"    },    \"products\": [        {            \"name\": \"Papel adesivo para etiquetas 1\",            \"quantity\": 3,            \"unitary_value\": 100.00        },        {            \"name\": \"Papel adesivo para etiquetas 2\",            \"quantity\": 1,            \"unitary_value\": 700.00        }    ],    \"volumes\": [        {            \"height\": 15,            \"width\": 20,            \"length\": 10,            \"weight\": 3.5        }    ],    \"options\": {        \"insurance_value\": 1000.00,        \"receipt\": false,        \"own_hand\": false,        \"reverse\": false,        \"non_commercial\": false,        \"invoice\": {            \"key\": \"31190307586261000184550010000092481404848162\"        },        \"platform\": \"Nome da Plataforma\",        \"tags\": [            {                \"tag\": \"Identificação do pedido na plataforma, exemplo: 1000007\",                \"url\": \"Link direto para o pedido na plataforma, se possível, caso contrário pode ser passado o valor null\"            }        ]    }}";

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<MelhorEnvioInserirFreteCarrinhoResponseDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_INSERIR_FRETE_CARRINHO, HttpMethod.POST, entity, MelhorEnvioInserirFreteCarrinhoResponseDto.class);

        System.out.println(response.getBody());


    }

    @Test
        // Faz a compra do frete no site do MelhorEnvio e ele passa a aparecer no carrinho de compras
    void checkoutFrete() {
        HttpHeaders headers = getHeaderConfiguration();
        String json = "{\"orders\":[\"243100ea-9f49-4f67-837c-7bb889e2ecef\"]}";

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_CHECKOUT_FRETE, HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
    }

    @Test
        // Gera o codigo de rastreio isso que seria Etiqueta
    void gerarEtiqueta() {
        HttpHeaders headers = getHeaderConfiguration();
        String json = "{\"orders\":[\"243100ea-9f49-4f67-837c-7bb889e2ecef\"]}";

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_GERAR_ETIQUETA, HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
    }


    @Test
    void imprimirEtiqueta() {
        HttpHeaders headers = getHeaderConfiguration();
        String exemplo = "{\"mode\":\"private\",\"orders\":[\"{{order_id}}\"]}";
        String json = "{\"mode\":\"private\",\"orders\":[\"243100ea-9f49-4f67-837c-7bb889e2ecef\"]}";

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(ApiConstantes.URL_SANDBOX_MELHOR_ENVIO_IMPRIMIR_ETIQUETA, HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
    }

    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ApiConstantes.TOKEN_SANDBOX_MELHOR_ENVIO);
        headers.set("User-Agent", "gustavopaco@gmail.com");
        return headers;
    }

    private List<MelhorEnvioConsultaFreteDto> formatResponse(List<MelhorEnvioConsultaFreteResponseDto> responseBody) {
        List<MelhorEnvioConsultaFreteDto> melhorEnvioConsultaFreteDtos = new ArrayList<>();
        if (responseBody != null) {
            melhorEnvioConsultaFreteDtos = responseBody.stream().map(melhorEnvioConsultaFreteResponseDto -> {
                if (melhorEnvioConsultaFreteResponseDto.error() == null) {
                    return mapperMelhorEnvio.toEntity(melhorEnvioConsultaFreteResponseDto);
                }
                return null;
            }).filter(Objects::nonNull).toList();
        }
        return melhorEnvioConsultaFreteDtos;
    }
}
