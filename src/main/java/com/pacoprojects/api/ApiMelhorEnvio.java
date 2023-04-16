package com.pacoprojects.api;

import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConfig;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioInserirFreteDto;
import com.pacoprojects.api.integration.melhor.envio.MelhorEnvioMapper;
import com.pacoprojects.api.integration.melhor.envio.request.checkout.frete.RequestMelhorEnvioCheckoutFreteDto;
import com.pacoprojects.api.integration.melhor.envio.request.consulta.frete.RequestMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.request.gerar.etiqueta.RequestMelhorEnvioGerarEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.request.imprimir.etiqueta.RequestMelhorEnvioImprimirEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho.*;
import com.pacoprojects.api.integration.melhor.envio.response.checkout.frete.ResponseMelhorEnvioCheckoutFreteDto;
import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.ResponseMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.response.imprimir.etiqueta.ResponseMelhorEnvioImprimirEtiquetaDto;
import com.pacoprojects.api.integration.melhor.envio.response.inserir.frete.carrinho.ResponseMelhorEnvioInserirFreteCarrinhoDto;
import com.pacoprojects.model.Endereco;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.VendaCompraRepository;
import com.pacoprojects.security.ApplicationConfig;
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


    private HttpHeaders getHeaderConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(melhorEnvioConfig.getToken());
        headers.set("User-Agent", melhorEnvioConfig.getEmailUserAgent());
        return headers;
    }

    public List<MelhorEnvioConsultaFreteDto> consultaFreteMelhorEnvio(RequestMelhorEnvioConsultaFreteDto requestMelhorEnvioConsultaFreteDto) {

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

    public ResponseMelhorEnvioImprimirEtiquetaDto imprimeMelhorEnvioEtiqueta(Long idVenda) {

        Optional<VendaCompra> optionalVendaCompra = repositoryVendaCompra.findById(idVenda);

        if (optionalVendaCompra.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe venda com esse código");
        }

        // Inserindo frete no Carrinho - Chamando Api
        MelhorEnvioInserirFreteDto inserirFreteDto = inserirFreteCarrinho(optionalVendaCompra.get());
        // Fazendo Checkout frete - Chamando Api
        checkoutFrete(inserirFreteDto.codigoEtiqueta(), idVenda);
        // Gerando Etiquta - Chamando Api
        gerarEtiqueta(inserirFreteDto.codigoEtiqueta(), idVenda);

        HttpHeaders headers = getHeaderConfiguration();

        RequestMelhorEnvioImprimirEtiquetaDto etiquetaDto = imprimirEtiquetaRequestDtoMapped(inserirFreteDto.codigoEtiqueta());

        HttpEntity<RequestMelhorEnvioImprimirEtiquetaDto> bodyHeaders = new HttpEntity<>(etiquetaDto, headers);

        ResponseEntity<ResponseMelhorEnvioImprimirEtiquetaDto> response = applicationConfig
                .getRestTemplateInstance()
                .postForEntity(melhorEnvioConfig.urlImprimirEtiqueta(), bodyHeaders, ResponseMelhorEnvioImprimirEtiquetaDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro no processo de Imprimir Etiqueta, código da Venda: " + idVenda + " , código da Etiqueta: " + inserirFreteDto.codigoEtiqueta());
        }

        repositoryVendaCompra.updateUrlEtiqueta(idVenda, response.getBody().url());
        return response.getBody();
    }

    private RequestMelhorEnvioImprimirEtiquetaDto imprimirEtiquetaRequestDtoMapped(String codigoEtiqueta) {
        return RequestMelhorEnvioImprimirEtiquetaDto
                .builder()
                .mode("private")
                .orders(List.of(codigoEtiqueta))
                .build();
    }

    private MelhorEnvioInserirFreteDto inserirFreteCarrinho(VendaCompra vendaCompra) {

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

    private void checkoutFrete(String codigoEtiqueta, Long idVendaCompra) {

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

    private void gerarEtiqueta(String codigoEtiqueta, Long idVendaCompra) {

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
}
