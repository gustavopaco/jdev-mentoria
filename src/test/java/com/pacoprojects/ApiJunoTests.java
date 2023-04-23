package com.pacoprojects;

import com.pacoprojects.api.ApiConstantes;
import com.pacoprojects.api.integration.juno.ApiJunoCobrancaService;
import com.pacoprojects.api.integration.juno.ApiJunoPixService;
import com.pacoprojects.api.integration.juno.ApiJunoWebHookService;
import com.pacoprojects.api.integration.juno.JunoAccessTokenService;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.RequestCobrancaJunoEnderecoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.ResponseCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.webhook.criar.request.RequestJunoCriarWebHook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiJunoTests {

    private final JunoAccessTokenService serviceAccessTokenJuno;
    private final ApiJunoPixService serviceJunoPix;
    private final ApiJunoCobrancaService serviceJunoBoleto;
    private final ApiJunoWebHookService serviceJunoWebHook;

    @Autowired
    public ApiJunoTests(JunoAccessTokenService serviceAccessTokenJuno, ApiJunoPixService serviceJunoPix, ApiJunoCobrancaService serviceJunoBoleto, ApiJunoWebHookService serviceJunoWebHook) {
        this.serviceAccessTokenJuno = serviceAccessTokenJuno;
        this.serviceJunoPix = serviceJunoPix;
        this.serviceJunoBoleto = serviceJunoBoleto;
        this.serviceJunoWebHook = serviceJunoWebHook;
    }

    @Test
    void testeGerarNovoBearerToken() {
        serviceAccessTokenJuno.apiGerarNovoToken();
    }

    @Test
    void testeGerarChavePixRandomica() {
        serviceJunoPix.apiGerarChavePix();
    }

    @Test
    void testeGerarBoleto() {

        RequestCobrancaJunoDto junoDto = RequestCobrancaJunoDto
                .builder()
                .idVenda(36L)
                .email("gustavopaco01@gmail.com")
                .description("Descricao de Boleto Teste para mim mesmo")
                .payerCpfCnpj("09360780065")
                .amount(new BigDecimal("96.50"))
                .installMents(6)
                .payerName("Fulano da Silva")
                .payerPhone("31993039064")
                .address(RequestCobrancaJunoEnderecoDto
                        .builder()
                        .rua("Rua Sargento Johnny da Silva")
                        .numero("200")
                        .complemento("Bloco 20")
                        .bairro("Betânia")
                        .cidade("Belo Horizonte")
                        .estado("MG")
                        .cep("30590253")
                        .build())
                .build();

        ResponseCobrancaJunoDto responseDto = serviceJunoBoleto.apiGerarCobrancaBoleto(junoDto);

        System.out.println(responseDto.url());
    }

    @Test
    void testeCriarWebhook() {

        RequestJunoCriarWebHook webHook = RequestJunoCriarWebHook
                .builder()
                .url(ApiConstantes.URL_AWS_API)
                .eventTypes(List.of("PAYMENT_NOTIFICATION", "BILL_PAYMENT_STATUS_CHANGED", "CHARGE_STATUS_CHANGED"))
                .build();

        serviceJunoWebHook.apiCriarWebHook(webHook);

    }

    @Test
    void testeListarWebHooks() {
        serviceJunoWebHook.apiListarWebHooks();
    }

    @Test
    void testeDeletarWebHook() {
        serviceJunoWebHook.deletarWebHook();
    }
}
