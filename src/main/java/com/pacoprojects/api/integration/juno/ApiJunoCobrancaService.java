package com.pacoprojects.api.integration.juno;

import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.ResponseCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaAddressDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaBillingDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestCriarCobrancaChargeDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request.RequestJunoCriarCobrancaDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response.ResponseCriarCobrancaChargeDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.response.ResponseJunoCriarCobrancaDto;
import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.model.BoletoJuno;
import com.pacoprojects.model.VendaCompra;
import com.pacoprojects.repository.BoletoJunoRepository;
import com.pacoprojects.repository.VendaCompraRepository;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
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

    public ResponseCobrancaJunoDto apiGerarBoleto(RequestCobrancaJunoDto cobrancaJunoDto) {

        VendaCompra vendaCompra = repositoryVendaCompra.findById(cobrancaJunoDto.idVenda())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma venda com esse código"));

        AccessTokenJuno accessTokenJuno = serviceJunoAccessToken.apiGerarNovoToken();

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        RequestJunoCriarCobrancaDto requestDto = apiGerarBoletoDtoMapped(cobrancaJunoDto, accessTokenJuno);

        HttpEntity<RequestJunoCriarCobrancaDto> bodyHeaders = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ResponseJunoCriarCobrancaDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlCobranca(), HttpMethod.POST, bodyHeaders, ResponseJunoCriarCobrancaDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao gerar o boleto.");
        }

        String linkPagamento = saveNewBoleto(response.getBody(), vendaCompra);

        return ResponseCobrancaJunoDto
                .builder()
                .url(linkPagamento)
                .build();
    }

    private RequestJunoCriarCobrancaDto apiGerarBoletoDtoMapped(RequestCobrancaJunoDto cobrancaJunoDto, AccessTokenJuno accessTokenJuno) {

        return RequestJunoCriarCobrancaDto.builder()
                .charge(RequestCriarCobrancaChargeDto
                        .builder()
                        .pixKey(accessTokenJuno.getPixKey())
                        .description(cobrancaJunoDto.description())
                        .amount(cobrancaJunoDto.amount())
                        .installments(cobrancaJunoDto.installMents())
                        .dueDate(LocalDate.now().plusDays(7).toString())
                        .fine(new BigDecimal("1"))
                        .interest("1.00")  // Pode ser que tenha que trocar para BigDecimal
                        .maxOverdueDays(10)
                        .paymentTypes(List.of("BOLETO_PIX"))
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
                                .postCode(cobrancaJunoDto.address().cep())
                                .build())
                        .document(cobrancaJunoDto.payerCpfCnpj())
                        .email(cobrancaJunoDto.email())
                        .phone(cobrancaJunoDto.payerPhone())
                        .build())
                .build();
    }

    private String saveNewBoleto(ResponseJunoCriarCobrancaDto cobrancaDto, VendaCompra vendaCompra) {

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
                    .idPix(charge.pix().id())
                    .payloadInBase64(charge.pix().payloadInBase64())
                    .imageInBase64(charge.pix().imageInBase64())
                    .recorrencia(qtdParcelas)
                    .build();

            boletos.add(boletoJuno);
            qtdParcelas++;
        }
        repositoryBoletoJuno.saveAll(boletos);
        return boletos.get(0).getLink();
    }

    private HttpHeaders getHeadersConfigurationCancelarBoleto(String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.set("X-Api-Version", "2");
        headers.set("X-Resource-Token", junoConfig.getXResourceToken());
        return  headers;
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

        // TODO trocar status do boleto para "CANCELLED"
        // TODO enviar email para o cliente falando que a compra foi cancelada

    }
}
