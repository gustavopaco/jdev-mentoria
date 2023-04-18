package com.pacoprojects.api.integration.melhor.envio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.api.integration.melhor.envio.cancelamento.etiqueta.request.RequestCancelamentoEtiquetaOrderDto;
import com.pacoprojects.api.integration.melhor.envio.cancelamento.etiqueta.request.RequestMelhorEnvioCancelamentoEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.checkout.frete.request.RequestMelhorEnvioCheckoutFreteDto;
import com.pacoprojects.api.integration.melhor.envio.checkout.frete.response.ResponseMelhorEnvioCheckoutFreteDto;
import com.pacoprojects.api.integration.melhor.envio.consulta.frete.request.RequestMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.consulta.frete.response.ResponseMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.gerar.etiqueta.request.RequestMelhorEnvioGerarEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.imprimir.etiqueta.request.RequestMelhorEnvioImprimirEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.imprimir.etiqueta.response.ResponseMelhorEnvioImprimirEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.request.*;
import com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.response.ResponseMelhorEnvioInserirFreteCarrinhoDto;
import com.pacoprojects.api.integration.melhor.envio.rastreio.pedido.request.RequestMelhorEnvioRastreioPedidoDto;
import com.pacoprojects.api.integration.melhor.envio.rastreio.pedido.response.ResponseMelhorEnvioRastreioPedidoDto;
import com.pacoprojects.model.Endereco;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.VendaCompraRepository;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.service.StatusRastreioService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApiMelhorEnvio {

    private final ApplicationConfig applicationConfig;
    private final MelhorEnvioMapper mapperMelhorEnvio;
    private final MelhorEnvioConfig melhorEnvioConfig;
    private final VendaCompraRepository repositoryVendaCompra;
    private final StatusRastreioService serviceStatusRastreio;


    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(melhorEnvioConfig.getToken());
        headers.set("User-Agent", melhorEnvioConfig.getEmailUserAgent());
        return headers;
    }

    public List<MelhorEnvioConsultaFreteDto> apiConsultaFreteMelhorEnvio(RequestMelhorEnvioConsultaFreteDto requestMelhorEnvioConsultaFreteDto) {

        HttpHeaders headers = getHeaderConfiguration();

        // Classe que junta o BODY que sera enviado como POSTRequest com o Header para ser enviado ao MelhorEnvio
        HttpEntity<RequestMelhorEnvioConsultaFreteDto> bodyAndHeaders = new HttpEntity<>(requestMelhorEnvioConsultaFreteDto, headers);

        // Classe responsavel por definir que a Response sera como List<ResponseMelhorEnvioConsultaFreteDto>, sem ela so podemos retornar objetos
        ParameterizedTypeReference<List<ResponseMelhorEnvioConsultaFreteDto>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<ResponseMelhorEnvioConsultaFreteDto>> response = applicationConfig
                .getRestTemplateInstance()
                .exchange((melhorEnvioConfig.urlConsultarFrete()), HttpMethod.POST, bodyAndHeaders, responseType);

        if (response.getStatusCode().value() != 200 || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "MelhorEnvioException");
        }

        return consultaFreteResponseDtoMapped(response.getBody());
    }

    private List<MelhorEnvioConsultaFreteDto> consultaFreteResponseDtoMapped(List<ResponseMelhorEnvioConsultaFreteDto> responseBody) {

        return responseBody.stream().map(responseMelhorEnvioConsultaFreteDto -> {
            if (responseMelhorEnvioConsultaFreteDto.error() == null) {
                return mapperMelhorEnvio.toDto(responseMelhorEnvioConsultaFreteDto);
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }

    public ResponseMelhorEnvioImprimirEtiquetaDto apiImprimeEtiquetaMelhorEnvio(Long idVenda) {

        Optional<VendaCompra> optionalVendaCompra = repositoryVendaCompra.findById(idVenda);

        if (optionalVendaCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe venda com esse código");
        }

        // Inserindo frete no Carrinho - Chamando Api
        MelhorEnvioInserirFreteDto inserirFreteDto = apiInserirFreteCarrinhoMelhorEnvio(optionalVendaCompra.get());
        // Fazendo Checkout frete - Chamando Api
        apiCheckoutFreteMelhorEnvio(inserirFreteDto.codigoEtiqueta(), idVenda);
        // Gerando Etiquta - Chamando Api
        apiGerarEtiquetaMelhorEnvio(inserirFreteDto.codigoEtiqueta(), idVenda);

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioImprimirEtiquetaDto etiquetaDto = imprimirEtiquetaRequestDtoMapped(inserirFreteDto.codigoEtiqueta());

        HttpEntity<RequestMelhorEnvioImprimirEtiquetaDto> bodyHeaders = new HttpEntity<>(etiquetaDto, headers);

        ResponseEntity<ResponseMelhorEnvioImprimirEtiquetaDto> response = applicationConfig
                .getRestTemplateInstance()
                .postForEntity(melhorEnvioConfig.urlImprimirEtiqueta(), bodyHeaders, ResponseMelhorEnvioImprimirEtiquetaDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro no processo de Imprimir Etiqueta, código da Venda: " + idVenda + " , código da Etiqueta: " + inserirFreteDto.codigoEtiqueta());
        }

        // Salvando a Url da etiqueta no Banco de dados
        repositoryVendaCompra.updateUrlEtiqueta(idVenda, response.getBody().url());

        apiGerarRastreioPedidoMelhorEnvio(optionalVendaCompra.get());

        return response.getBody();
    }

    private RequestMelhorEnvioImprimirEtiquetaDto imprimirEtiquetaRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioImprimirEtiquetaDto
                .builder()
                .mode("private")
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    private MelhorEnvioInserirFreteDto apiInserirFreteCarrinhoMelhorEnvio(VendaCompra vendaCompra) {

        RequestMelhorEnvioInserirCarrinhoDtoDto requestDto = inserirFreteCarrinhoRequestDtoMapped(vendaCompra);

        HttpHeaders headers = getHeaderConfiguration();

        HttpEntity<RequestMelhorEnvioInserirCarrinhoDtoDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponseMelhorEnvioInserirFreteCarrinhoDto> response = applicationConfig
                    .getRestTemplateInstance().postForEntity(melhorEnvioConfig.urlInserirFreteCarrinho(), bodyHeaders, ResponseMelhorEnvioInserirFreteCarrinhoDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processo de Inserir venda ao carrinho, código da Venda: " + vendaCompra.getId());
        }

        MelhorEnvioInserirFreteDto melhorEnvioDto = mapperMelhorEnvio.toDto(response.getBody());

        //Note: Salvando código da Etiqueta no banco de dados de acordo com o codigo da Venda, apos o retorno da Api
        repositoryVendaCompra.updateCodigoEtiqueta(vendaCompra.getId(), melhorEnvioDto.codigoEtiqueta());

        return melhorEnvioDto;
    }

    private RequestMelhorEnvioInserirCarrinhoDtoDto inserirFreteCarrinhoRequestDtoMapped(VendaCompra vendaCompra) {
        Endereco enderecoEmpresa = vendaCompra.getEmpresa().getEnderecos().stream().toList().get(0);

        List<RequestInserirFreteCarrinhoProdutosDto> produtosRequestDtos = new ArrayList<>();
        List<RequestInserirFreteCarrinhoVolumesDto> volumesRequestDtos = new ArrayList<>();
        List<RequestInserirFreteCarrinhoTagsDto> tagsRequestDtos = new ArrayList<>();

        vendaCompra.getItemVendaCompras().forEach(itemVendaCompra -> {
            produtosRequestDtos.add(RequestInserirFreteCarrinhoProdutosDto
                    .builder()
                    .name(itemVendaCompra.getProduto().getNome())
                    .quantity(itemVendaCompra.getQuantidade().intValue())
                    .unitary_value(itemVendaCompra.getProduto().getValorVenda())
                    .build());
            volumesRequestDtos.add(RequestInserirFreteCarrinhoVolumesDto
                    .builder()
                    .height(itemVendaCompra.getProduto().getAltura())
                    .width(itemVendaCompra.getProduto().getLargura())
                    .length(itemVendaCompra.getProduto().getProfundidade())
                    .weight(BigDecimal.valueOf(itemVendaCompra.getProduto().getPeso()))
                    .build());
        });

        tagsRequestDtos.add(RequestInserirFreteCarrinhoTagsDto
                .builder()
                .url("Identificação do pedido na plataforma: " + vendaCompra.getId())
                .tag(null)
                .build());

        return RequestMelhorEnvioInserirCarrinhoDtoDto
                .builder()
                .agency(49)
                .service(vendaCompra.getServicoTransportadora())
                .from(RequestInserirFreteCarrinhoFromToDto
                        .builder()
                        .name(vendaCompra.getEmpresa().getNomeFantasia())
                        .phone(vendaCompra.getEmpresa().getTelefones().stream().toList().get(0).getNumero())
                        .email(vendaCompra.getEmpresa().getEmail())
                        .company_document(vendaCompra.getEmpresa().getCnpj())
                        .state_register(vendaCompra.getEmpresa().getInscricaoEstadual())
                        .address(enderecoEmpresa.getRua())
                        .complement(enderecoEmpresa.getComplemento())
                        .number(enderecoEmpresa.getNumero())
                        .district(enderecoEmpresa.getBairro())
                        .city(enderecoEmpresa.getCidade())
                        .country_id(enderecoEmpresa.getPais())
                        .postal_code(enderecoEmpresa.getCep())
                        .note("n/a")
                        .build())
                .to(RequestInserirFreteCarrinhoFromToDto
                        .builder()
                        .name(vendaCompra.getPessoa().getNome())
                        .phone(vendaCompra.getPessoa().getTelefones().stream().toList().get(0).getNumero())
                        .email(vendaCompra.getPessoa().getEmail())
                        .document(vendaCompra.getPessoa().getCpf())
                        .address(vendaCompra.getEnderecoEntrega().getRua())
                        .complement(vendaCompra.getEnderecoEntrega().getComplemento())
                        .number(vendaCompra.getEnderecoEntrega().getNumero())
                        .district(vendaCompra.getEnderecoEntrega().getBairro())
                        .city(vendaCompra.getEnderecoEntrega().getCidade())
                        .state_abbr(vendaCompra.getEnderecoEntrega().getEstado())
                        .country_id(vendaCompra.getEnderecoEntrega().getPais())
                        .postal_code(vendaCompra.getEnderecoEntrega().getCep())
                        .note("n/a")
                        .build())
                .products(produtosRequestDtos)
                .volumes(volumesRequestDtos)
                .options(RequestInserirFreteCarrinhoOptionsDto
                        .builder()
                        .insurance_value(vendaCompra.getValorTotal())
                        .receipt(false)
                        .own_hand(false)
                        .reverse(false)
                        .non_commercial(true)
//                        .invoice(new RequestInserirFreteCarrinhoInvoiceDto(vendaCompra.getNotaFiscalVenda().getNumero()))
                        .platform(vendaCompra.getEmpresa().getNomeFantasia())
                        .tags(tagsRequestDtos)
                        .build())
                .build();
    }

    private void apiCheckoutFreteMelhorEnvio(String codigoEtiqueta, Long idVendaCompra) {

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioCheckoutFreteDto requestDto = checkoutFreteRequestDtoMapped(codigoEtiqueta);

        HttpEntity<RequestMelhorEnvioCheckoutFreteDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponseMelhorEnvioCheckoutFreteDto> response = applicationConfig
                .getRestTemplateInstance()
                .postForEntity(melhorEnvioConfig.urlCheckoutFrete(), bodyHeaders, ResponseMelhorEnvioCheckoutFreteDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro no processo de Checkout da Etiqueta, código da Venda: " + idVendaCompra + " , código da Etiqueta: " + codigoEtiqueta);
        }
    }

    private RequestMelhorEnvioCheckoutFreteDto checkoutFreteRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioCheckoutFreteDto
                .builder()
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    private void apiGerarEtiquetaMelhorEnvio(String codigoEtiqueta, Long idVendaCompra) {

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioGerarEtiquetaDto requestDto = gerarEtiquetaRequestDtoMapped(codigoEtiqueta);

        HttpEntity<RequestMelhorEnvioGerarEtiquetaDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .postForEntity(melhorEnvioConfig.urlGerarEtiqueta(), bodyHeaders, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro no processo de Gerar Etiqueta, código da Venda: " + idVendaCompra + " , código da Etiqueta: " + codigoEtiqueta);
        }
    }

    private RequestMelhorEnvioGerarEtiquetaDto gerarEtiquetaRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioGerarEtiquetaDto
                .builder()
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    public void apiCancelarEtiquetaMelhorEnvio(Long idVenda) {
        Optional<VendaCompra> optionalVendaCompra = repositoryVendaCompra.findById(idVenda);

        if (optionalVendaCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe venda com esse código.");
        }

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioCancelamentoEtiquetaDto requestDto = cancelarEtiquetaRequestDtoMapped(optionalVendaCompra.get().getCodigoEtiqueta());

        HttpEntity<RequestMelhorEnvioCancelamentoEtiquetaDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

            ResponseEntity<String> response = applicationConfig
                    .getRestTemplateInstance()
                    .exchange(melhorEnvioConfig.urlCancelamentoEtiquetas(), HttpMethod.POST, bodyHeaders, String.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar cancelamento de etiqueta");
            }

    }

    private RequestMelhorEnvioCancelamentoEtiquetaDto cancelarEtiquetaRequestDtoMapped(String orderId) {
        return RequestMelhorEnvioCancelamentoEtiquetaDto
                .builder()
                .order(RequestCancelamentoEtiquetaOrderDto
                        .builder()
                        .id(orderId)
                        .description("Cancelamento de teste")
                        .reason_id(2)
                        .build())
                .build();
    }

    public void apiGerarRastreioPedidoMelhorEnvio(VendaCompra vendaCompra) {

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioRastreioPedidoDto requestDto = rastreioPedidoRequestDtoMapped(vendaCompra.getCodigoEtiqueta());

        HttpEntity<RequestMelhorEnvioRastreioPedidoDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(melhorEnvioConfig.urlRastreioPedido(), HttpMethod.POST, bodyHeaders, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao processar criação de rastreamento do pedido");
        }

        String urlRastreio = melhorEnvioConfig.getUrlRastreio() + rastreioPedidoResponseJsonMapped(response);

        // Gerando Status de Rastreio para a venda
        serviceStatusRastreio.saveNewStatusRastreio(vendaCompra, urlRastreio);
    }

    public ResponseMelhorEnvioRastreioPedidoDto apiConsultaRastreioPedidoMelhorEnvio(Long idVenda) {

        Optional<VendaCompra> optionalVendaCompra = repositoryVendaCompra.findById(idVenda);

        if (optionalVendaCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe venda com esse código.");
        }

        return serviceStatusRastreio.rastrearVenda(idVenda);
    }

    private RequestMelhorEnvioRastreioPedidoDto rastreioPedidoRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioRastreioPedidoDto
                .builder()
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    private String rastreioPedidoResponseJsonMapped(ResponseEntity<String> response) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            JsonNode trackingNode = rootNode.get(rootNode.fieldNames().next()).get("melhorenvio_tracking");
            return trackingNode.asText();
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possivel ler o rastreio do pedido.");
        }

    }
}
