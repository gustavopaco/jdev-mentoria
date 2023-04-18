package com.pacoprojects;

import com.pacoprojects.api.integration.juno.JunoAccessTokenService;
import com.pacoprojects.api.integration.juno.JunoPixService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiJunoTests {

    private final JunoAccessTokenService serviceAccessTokenJuno;
    private final JunoPixService serviceJunoBoleto;

    @Autowired
    public ApiJunoTests(JunoAccessTokenService serviceAccessTokenJuno, JunoPixService serviceJunoBoleto) {
        this.serviceAccessTokenJuno = serviceAccessTokenJuno;
        this.serviceJunoBoleto = serviceJunoBoleto;
    }

    @Test
    void testeGerarNovoBearerToken() {
        serviceAccessTokenJuno.apiGerarNovoToken();
    }

    @Test
    void testeGerarChavePixRandomica() {
        serviceJunoBoleto.apiGerarChavePix();
    }
}
