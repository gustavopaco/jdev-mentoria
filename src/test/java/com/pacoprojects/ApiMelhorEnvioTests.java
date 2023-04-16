package com.pacoprojects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConfig;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.cancelamento.etiqueta.RequestCancelamentoEtiquetaOrderDto;
import com.pacoprojects.api.integration.melhor.envio.request.cancelamento.etiqueta.RequestMelhorEnvioCancelamentoEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.RequestConsultaFreteFromToDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.RequestConsultaFreteProdutosDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.RequestMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.request.rastreio.pedido.RequestMelhorEnvioRastreioPedidoDto;
import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.ResponseMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.response.inserir.frete.carrinho.ResponseMelhorEnvioInserirFreteCarrinhoDto;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.VendaCompraRepository;
import com.pacoprojects.security.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiMelhorEnvioTests {
    private final ApplicationConfig applicationConfig;
    private final MelhorEnvioConfig melhorEnvioConfig;
    private final MelhorEnvioMapper mapperMelhorEnvio;
    private final VendaCompraRepository repositoryVendaCompra;

    @Autowired
    public ApiMelhorEnvioTests(ApplicationConfig applicationConfig, MelhorEnvioConfig melhorEnvioConfig, MelhorEnvioMapper mapperMelhorEnvio, VendaCompraRepository repositoryVendaCompra) {
        this.applicationConfig = applicationConfig;
        this.melhorEnvioConfig = melhorEnvioConfig;
        this.mapperMelhorEnvio = mapperMelhorEnvio;
        this.repositoryVendaCompra = repositoryVendaCompra;
    }

    @Test
    void consultaFreteMelhorEnvio() {

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioConsultaFreteDto requestMelhorEnvioConsultaFreteDto = RequestMelhorEnvioConsultaFreteDto
                .builder()
                .from(new RequestConsultaFreteFromToDto("30590-253"))
                .to(new RequestConsultaFreteFromToDto("65068-097"))
                .products(List.of(
                        new RequestConsultaFreteProdutosDto("x", 11, 17, 11, new BigDecimal("0.3"), new BigDecimal("10.1"), 1)
                ))
                .build();

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<RequestMelhorEnvioConsultaFreteDto> entity = new HttpEntity<>(requestMelhorEnvioConsultaFreteDto, headers);

        // Classe responsavel por definir a Response como List<ResponseMelhorEnvioConsultaFreteDto>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<ResponseMelhorEnvioConsultaFreteDto>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<ResponseMelhorEnvioConsultaFreteDto>> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(melhorEnvioConfig.urlConsultarFrete(), HttpMethod.POST, entity, responseType);

        List<ResponseMelhorEnvioConsultaFreteDto> body = response.getBody();
        System.out.println(formatResponse(body));
    }

    @Test
        // Insere o frete no carrinho
    void inserirFreteCarrinhoMelhorEnvio() {
        HttpHeaders headers = getHeaderConfiguration();

        String json = "{    \"service\": 3,    \"agency\": 49,    \"from\": {        \"name\": \"Nome do remetente\",        \"phone\": \"53984470102\",        \"email\": \"contato@melhorenvio.com.br\",        \"document\": \"16571478358\",        \"company_document\": \"89794131000100\",        \"state_register\": \"123456\",        \"address\": \"Endereço do remetente\",        \"complement\": \"Complemento\",        \"number\": \"1\",        \"district\": \"Bairro\",        \"city\": \"São Paulo\",        \"country_id\": \"BR\",        \"postal_code\": \"01002001\",        \"note\": \"observação\"    },    \"to\": {        \"name\": \"Nome do destinatário\",        \"phone\": \"53984470102\",        \"email\": \"contato@melhorenvio.com.br\",        \"document\": \"25404918047\",        \"company_document\": \"07595604000177\",        \"state_register\": \"123456\",        \"address\": \"Endereço do destinatário\",        \"complement\": \"Complemento\",        \"number\": \"2\",        \"district\": \"Bairro\",        \"city\": \"Porto Alegre\",        \"state_abbr\": \"RS\",        \"country_id\": \"BR\",        \"postal_code\": \"90570020\",        \"note\": \"observação\"    },    \"products\": [        {            \"name\": \"Papel adesivo para etiquetas 1\",            \"quantity\": 3,            \"unitary_value\": 100.00        },        {            \"name\": \"Papel adesivo para etiquetas 2\",            \"quantity\": 1,            \"unitary_value\": 700.00        }    ],    \"volumes\": [        {            \"height\": 15,            \"width\": 20,            \"length\": 10,            \"weight\": 3.5        }    ],    \"options\": {        \"insurance_value\": 1000.00,        \"receipt\": false,        \"own_hand\": false,        \"reverse\": false,        \"non_commercial\": false,        \"invoice\": {            \"key\": \"31190307586261000184550010000092481404848162\"        },        \"platform\": \"Nome da Plataforma\",        \"tags\": [            {                \"tag\": \"Identificação do pedido na plataforma, exemplo: 1000007\",                \"url\": \"Link direto para o pedido na plataforma, se possível, caso contrário pode ser passado o valor null\"            }        ]    }}";

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<ResponseMelhorEnvioInserirFreteCarrinhoDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(melhorEnvioConfig.urlInserirFreteCarrinho(), HttpMethod.POST, entity, ResponseMelhorEnvioInserirFreteCarrinhoDto.class);

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
                .exchange(melhorEnvioConfig.urlCheckoutFrete(), HttpMethod.POST, entity, String.class);

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
                .exchange(melhorEnvioConfig.urlGerarEtiqueta(), HttpMethod.POST, entity, String.class);

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
                .exchange(melhorEnvioConfig.urlImprimirEtiqueta(), HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
    }

    @Test
    void listarAgencias() {
        HttpHeaders headers = getBasicHeaderConfiguration();

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        
        ResponseEntity<String> response = null;
        
            response = applicationConfig
                    .getRestTemplateInstance()
                    .exchange(melhorEnvioConfig.urlListarAgencias(), HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());
    }

    @Test
    void cancelamentoEtiquetas() {
        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioCancelamentoEtiquetaDto requestDto = RequestMelhorEnvioCancelamentoEtiquetaDto
                .builder()
                .order(RequestCancelamentoEtiquetaOrderDto
                        .builder()
                        .id("da848db1-d422-419d-afa2-876ffc440ccc")
                        .description("Cancelamento de teste")
                        .reason_id(2)
                        .build())
                .build();

        // Classe que junta o BODY do request com o Header para ser enviado ao
        HttpEntity<RequestMelhorEnvioCancelamentoEtiquetaDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(melhorEnvioConfig.urlCancelamentoEtiquetas(), HttpMethod.POST, bodyHeaders, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar cancelamento de etiqueta");
        }
        System.out.println(response.getBody());
    }

    @Test
    void rastreioPedido() throws JsonProcessingException {

        Optional<VendaCompra> optionalVendaCompra = repositoryVendaCompra.findById(36L);

        if (optionalVendaCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe compra com esse código.");
        }

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioRastreioPedidoDto requestDto = rastreioPedidoRequestDtoMapped(optionalVendaCompra.get().getCodigoEtiqueta());

        HttpEntity<RequestMelhorEnvioRastreioPedidoDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(melhorEnvioConfig.urlRastreioPedido(), HttpMethod.POST, bodyHeaders, String.class );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar cancelamento de etiqueta");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.getBody());
        JsonNode trackingNode = rootNode.get(rootNode.fieldNames().next()).get("melhorenvio_tracking");
        String melhorenvioTracking = trackingNode.asText();

        System.out.println(melhorEnvioConfig.urlRastreioPedido() + melhorenvioTracking);
    }

    private RequestMelhorEnvioRastreioPedidoDto rastreioPedidoRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioRastreioPedidoDto
                .builder()
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(melhorEnvioConfig.getToken());
        headers.set("User-Agent", melhorEnvioConfig.getEmailUserAgent());
        return headers;
    }

    private HttpHeaders getBasicHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
//        headers.setContentType(MediaType.TEXT_PLAIN);
//        headers.setBearerAuth(melhorEnvioConfig.getToken());
        headers.set("User-Agent", melhorEnvioConfig.getEmailUserAgent());
        return headers;
    }

    private List<MelhorEnvioConsultaFreteDto> formatResponse(List<ResponseMelhorEnvioConsultaFreteDto> responseBody) {
        List<MelhorEnvioConsultaFreteDto> melhorEnvioConsultaFreteDtos = new ArrayList<>();
        if (responseBody != null) {
            melhorEnvioConsultaFreteDtos = responseBody.stream().map(responseMelhorEnvioConsultaFreteDto -> {
                if (responseMelhorEnvioConsultaFreteDto.error() == null) {
                    return mapperMelhorEnvio.toDto(responseMelhorEnvioConsultaFreteDto);
                }
                return null;
            }).filter(Objects::nonNull).toList();
        }
        return melhorEnvioConsultaFreteDtos;
    }
}
