package com.pacoprojects.api.integration.juno;

import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.ResponseCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaAddressDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaBillingDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaChargeDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestJunoCriarCobrancaDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response.ResponseCriarCobrancaChargeDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response.ResponseJunoCriarCobrancaDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.StatusCartaoJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.request.*;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.response.ResponsePagarCobrancaDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.cartao.response.ResponsePagarCobrancaPayment;
import com.pacoprojects.api.integration.juno.error.ResponseErrorDetailsDto;
import com.pacoprojects.enums.StatusVendaCompra;
import com.pacoprojects.enums.TipoPagamento;
import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.model.BoletoJuno;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.BoletoJunoRepository;
import com.pacoprojects.repository.VendaCompraRepository;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.util.UtilFunc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiJunoCobrancaService {

    private final JunoAccessTokenService serviceJunoAccessToken;
    private final JunoConfig junoConfig;
    private final ApplicationConfig applicationConfig;
    private final VendaCompraRepository repositoryVendaCompra;
    private final BoletoJunoRepository repositoryBoletoJuno;

    public ResponseCobrancaJunoDto apiGerarCobrancaBoleto(RequestCobrancaJunoDto cobrancaJunoDto) {

        VendaCompra vendaCompra = repositoryVendaCompra.findById(cobrancaJunoDto.idVenda())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma venda com esse código"));

        AccessTokenJuno accessTokenJuno = serviceJunoAccessToken.apiGerarNovoToken();

        RequestJunoCriarCobrancaDto requestDto = gerarCobrancaBoletoDtoMapped(cobrancaJunoDto, accessTokenJuno);

        ResponseJunoCriarCobrancaDto response = apiChamadaGerarCobranca(requestDto, accessTokenJuno);

        List<BoletoJuno> boletoJunos = saveNewCobranca(response, vendaCompra, TipoPagamento.BOLETO_PIX);

        String linkPagamento = boletoJunos.get(0).getLink();

        return ResponseCobrancaJunoDto
                .builder()
                .url(linkPagamento)
                .build();
    }

    private ResponseJunoCriarCobrancaDto apiChamadaGerarCobranca(RequestJunoCriarCobrancaDto requestDto, AccessTokenJuno accessTokenJuno) {

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        HttpEntity<RequestJunoCriarCobrancaDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponseJunoCriarCobrancaDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlCobranca(), HttpMethod.POST, bodyHeaders, ResponseJunoCriarCobrancaDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            if (response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao gerar o cobrança.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getJunoErrorsMessage(response.getBody().details()));
        }
        return response.getBody();
    }

    private RequestJunoCriarCobrancaDto gerarCobrancaBoletoDtoMapped(RequestCobrancaJunoDto cobrancaJunoDto, AccessTokenJuno accessTokenJuno) {

        return RequestJunoCriarCobrancaDto.builder()
                .charge(RequestCriarCobrancaChargeDto
                        .builder()
                        .pixKey(accessTokenJuno.getPixKey())
                        .description(cobrancaJunoDto.description())
                        .amount(UtilFunc.formatValorTotalParcelas(cobrancaJunoDto.amount(), cobrancaJunoDto.installMents()))
                        .installments(cobrancaJunoDto.installMents())
                        .dueDate(LocalDate.now().plusMonths(1).toString())
                        .fine(new BigDecimal("1"))
                        .interest("1.00")  // Pode ser que tenha que trocar para BigDecimal
                        .maxOverdueDays(10)
                        .paymentTypes(List.of(TipoPagamento.BOLETO_PIX.toString()))
                        .build())
                .billing(RequestCriarCobrancaBillingDto
                        .builder()
                        .name(cobrancaJunoDto.payerName())
                        .address(RequestCriarCobrancaAddressDto
                                .builder()
                                .street(cobrancaJunoDto.address().rua())
                                .number(cobrancaJunoDto.address().numero())
                                .complement(cobrancaJunoDto.address().complemento())
                                .neighborhood(cobrancaJunoDto.address().bairro())
                                .city(cobrancaJunoDto.address().cidade())
                                .state(cobrancaJunoDto.address().estado())
                                .postCode(UtilFunc.formatCep(cobrancaJunoDto.address().cep()))
                                .build())
                        .document(UtilFunc.formatCpfCpnj(cobrancaJunoDto.payerCpfCnpj()))
                        .email(cobrancaJunoDto.email())
                        .phone(cobrancaJunoDto.payerPhone())
                        .build())
                .build();
    }

    private  List<BoletoJuno> saveNewCobranca(ResponseJunoCriarCobrancaDto cobrancaDto, VendaCompra vendaCompra, TipoPagamento tipoPagamento) {

        int qtdParcelas = 1;
        List<BoletoJuno> boletos = new ArrayList<>();
        for (ResponseCriarCobrancaChargeDto charge : cobrancaDto._embedded().charges()) {
            BoletoJuno boletoJuno = BoletoJuno
                    .builder()
                    .empresa(vendaCompra.getEmpresa())
                    .vendaCompra(vendaCompra)
                    .code(charge.code())
                    .link(charge.link())
                    .dataVencimento(LocalDate.parse(charge.dueDate()).atStartOfDay())
                    .valor(new BigDecimal(charge.amount()))
                    .checkoutUrl(charge.checkoutUrl())
                    .installmentLink(charge.installmentLink())
                    .idChrBoleto(charge.id())
                    .chargeICartao(tipoPagamento.equals(TipoPagamento.CREDIT_CARD) ? charge.id() : null)
                    .idPix(charge.pix() != null ? charge.pix().id() : null)
                    .payloadInBase64(charge.pix() != null ? charge.pix().payloadInBase64() : null)
                    .imageInBase64(charge.pix() != null ? charge.pix().imageInBase64() : null)
                    .recorrencia(qtdParcelas)
                    .build();

            boletos.add(boletoJuno);
            qtdParcelas++;
        }

        return repositoryBoletoJuno.saveAll(boletos);
    }

    private HttpHeaders getHeadersConfigurationCancelarBoleto(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.set("X-Api-Version", "2");
        headers.set("X-Resource-Token", junoConfig.getXResourceToken());
        return headers;
    }

    public void apiCancelarBoleto(Long idBoleto) {

        AccessTokenJuno accessTokenJuno = serviceJunoAccessToken.apiGerarNovoToken();

        BoletoJuno boletoJuno = repositoryBoletoJuno.findById(idBoleto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Boleto não foi encontrado no banco."));

        // TODO verificar se o status do boleto ja esta pago "PAID" ou se ja esta cancelado "CANCELLED"

        HttpHeaders headers = getHeadersConfigurationCancelarBoleto(accessTokenJuno.getAccess_token());

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlCancelarCobranca(boletoJuno.getIdChrBoleto()), HttpMethod.PUT, request, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao cancelar o boleto entre em contato com o administrador.");
        }

        repositoryBoletoJuno.deleteById(idBoleto);

        // TODO trocar status do boleto para "CANCELLED"
        // TODO enviar email para o cliente falando que a compra foi cancelada
    }

    public StatusCartaoJunoDto apiGerarCobrancaCartao(RequestCartaoDto requestCartaoDto) {
        VendaCompra vendaCompra = repositoryVendaCompra.findById(requestCartaoDto.idVenda())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Venda não foi encontrada"));

        AccessTokenJuno accessTokenJuno = serviceJunoAccessToken.apiGerarNovoToken();

        RequestJunoCriarCobrancaDto requestDto = gerarCobrancaCartaoDtoMapped(requestCartaoDto, vendaCompra, accessTokenJuno);

        ResponseJunoCriarCobrancaDto response = apiChamadaGerarCobranca(requestDto, accessTokenJuno);

        List<BoletoJuno> boletoJunos = saveNewCobranca(response, vendaCompra, TipoPagamento.CREDIT_CARD);

        return apiPagarCobrancaCartao(requestCartaoDto, vendaCompra, boletoJunos, accessTokenJuno);

    }

    private RequestJunoCriarCobrancaDto gerarCobrancaCartaoDtoMapped(RequestCartaoDto requestCartaoDto, VendaCompra vendaCompra, AccessTokenJuno accessTokenJuno) {

        return RequestJunoCriarCobrancaDto
                .builder()
                .charge(RequestCriarCobrancaChargeDto
                        .builder()
                        .pixKey(accessTokenJuno.getPixKey())
                        .description("Pagamento da venda: " + requestCartaoDto.idVenda())
                        .amount(UtilFunc.formatValorTotalParcelas(requestCartaoDto.valorTotal(), requestCartaoDto.parcelas()))
                        .installments(requestCartaoDto.parcelas())
                        .dueDate(LocalDate.now().plusDays(7).toString())
                        .fine(new BigDecimal("1"))
                        .interest("1.00")
                        .maxOverdueDays(7)
                        .paymentTypes(List.of(TipoPagamento.CREDIT_CARD.toString()))
                        .build())
                .billing(RequestCriarCobrancaBillingDto
                        .builder()
                        .name(requestCartaoDto.cardData().holderName())
                        .document(UtilFunc.formatCpfCpnj(requestCartaoDto.cpf()))
//                        .email(vendaCompra.getPessoa().getEmail())
                        .email("gustavopaco01@gmail.com")
                        .phone(vendaCompra.getPessoa().getTelefones().stream().toList().get(0).getNumero())
                        .address(RequestCriarCobrancaAddressDto
                                .builder()
                                .street(requestCartaoDto.address().street())
                                .number(requestCartaoDto.address().number())
                                .neighborhood(requestCartaoDto.address().neighborhood())
                                .city(requestCartaoDto.address().city())
                                .state(requestCartaoDto.address().state())
                                .postCode(UtilFunc.formatCep(requestCartaoDto.address().postCode()))
                                .build())
                        .build())
                .build();
    }

    private StatusCartaoJunoDto apiPagarCobrancaCartao(RequestCartaoDto requestCartaoDto, VendaCompra vendaCompra, List<BoletoJuno> boletoJunos, AccessTokenJuno accessTokenJuno) {

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        BoletoJuno boletoJuno = boletoJunos.get(0);

        RequestJunoPagarCobrancaCartao requestDto = pagarCobrancaCartaoDtoMapped(requestCartaoDto, boletoJuno);

        HttpEntity<RequestJunoPagarCobrancaCartao> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponsePagarCobrancaDto> response = null;
        ResponseEntity<String> responseJson = null;
        try {
            response = applicationConfig.getRestTemplateInstance().exchange(junoConfig.urlPagarCobrancaCartao(), HttpMethod.POST, bodyHeaders, ResponsePagarCobrancaDto.class);
        } catch (HttpStatusCodeException exception) {

            // Caso felhe ao tentar realizar uma compra estou cancelando os boletos
            boletoJunos.forEach(boleto -> apiCancelarBoleto(boleto.getId()));
            ResponsePagarCobrancaDto errorObject = exception.getResponseBodyAs(ResponsePagarCobrancaDto.class);
            if (errorObject != null && errorObject.details() != null) {
                return StatusCartaoJunoDto
                        .builder()
                        .status("FAILED")
                        .message(errorObject.details().get(0).message())
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível pagar a cobrança");
        }

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível pagar a cobrança");
        }

        if (response.getBody() != null && response.getBody().payments().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum pagamento foi retornado para processamento.");
        }

        ResponsePagarCobrancaPayment payment = response.getBody().payments().get(0);

        switch (payment.status()) {
            case "DECLINED" -> {
                return new StatusCartaoJunoDto("DECLINED","Pagamento não autorizado pela instituição responsável pelo cartão de credito, no caso a emissora do seu cartão.");
            }
            case "CONFIRMED" -> {
                // TODO liberar produto, enviar emails, atualizar venda no banco de dados para pago, diminuir estoque no banco de dados, emitir nota fiscal, criar frete na transportadora, enviar email para o setor de estoque preparar o produto
                processarPagamentoConfirmed(vendaCompra, boletoJunos);
                return new StatusCartaoJunoDto("CONFIRMED","Pagamento realizado com sucesso.");
            }
            case "CUSTOMER_PAID_BACK" -> {
                // TODO Nesse caso, deveria criar uma rotina para bloquear a compra do cliente
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento estornado a pedido do cliente");
            }
            case "BANK_PAID_BACK" -> {
                // TODO Nesse caso, deveria criar uma rotina para bloquear a compra do cliente
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento estornado a pedido do banco");
            }
            case "PARTIALLY_REFUNDED" -> {
                // TODO Nesse caso, deveria criar uma rotina para bloquear a compra do cliente
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento parcialmente estornado");
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum dos tipos de pagamento retornados");
        }
    }

    private RequestJunoPagarCobrancaCartao pagarCobrancaCartaoDtoMapped(RequestCartaoDto requestCartaoDto, BoletoJuno boletoJuno) {
        return RequestJunoPagarCobrancaCartao
                .builder()
                .chargeId(boletoJuno.getChargeICartao())
                .creditCardDetails(RequestPagarCobrancaCartaoDetailsDto
                        .builder()
                        .creditCardHash(requestCartaoDto.cardHash())
                        .build())
                .billing(RequestPagarCobrancaCartaoBilling
                        .builder()
                        .delayed(false)
//                        .email(requestCartaoDto.email())
                        .email("gustavopaco01@gmail.com")
                        .address(RequestPagarCobrancaCartaoAddress
                                .builder()
                                .street(requestCartaoDto.address().street())
                                .number(requestCartaoDto.address().number())
                                .neighborhood(requestCartaoDto.address().neighborhood())
                                .city(requestCartaoDto.address().city())
                                .state(requestCartaoDto.address().state())
                                .postCode(UtilFunc.formatCep(requestCartaoDto.address().postCode()))
                                .build())
                        .build())
                .build();
    }

    private void processarPagamentoConfirmed(VendaCompra vendaCompra, List<BoletoJuno> boletoJunos) {
        boletoJunos.forEach(boletoJuno -> repositoryBoletoJuno.updateBoletoStatusQuitadoTrue(boletoJuno.getId()));
        repositoryVendaCompra.updateStatusVendaFinalizada(vendaCompra.getId(), StatusVendaCompra.FINALIZADA);
    }



    private String getJunoErrorsMessage(List<ResponseErrorDetailsDto> detailsDtos) {
        StringBuilder builder = new StringBuilder();
        detailsDtos.forEach(responseErrorDetailsDto -> builder.append(responseErrorDetailsDto.message()).append("\n"));
        return builder.substring(0, builder.length() -1);
    }
}
