package com.pacoprojects;

import com.pacoprojects.api.integration.juno.ApiJunoBoletoService;
import com.pacoprojects.api.integration.juno.JunoAccessTokenService;
import com.pacoprojects.api.integration.juno.JunoPixService;
import com.pacoprojects.api.integration.juno.criar.cobranca.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.criar.cobranca.RequestCobrancaJunoEnderecoDto;
import com.pacoprojects.api.integration.juno.criar.cobranca.ResponseCobrancaJunoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiJunoTests {

    private final JunoAccessTokenService serviceAccessTokenJuno;
    private final JunoPixService serviceJunoPix;
    private final ApiJunoBoletoService serviceJunoBoleto;

    @Autowired
    public ApiJunoTests(JunoAccessTokenService serviceAccessTokenJuno, JunoPixService serviceJunoPix, ApiJunoBoletoService serviceJunoBoleto) {
        this.serviceAccessTokenJuno = serviceAccessTokenJuno;
        this.serviceJunoPix = serviceJunoPix;
        this.serviceJunoBoleto = serviceJunoBoleto;
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
                .installMents(6L)
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

        ResponseCobrancaJunoDto responseDto = serviceJunoBoleto.apiGerarBoleto(junoDto);

        System.out.println(responseDto.url());
    }
}
