package com.pacoprojects;

import com.pacoprojects.api.integration.juno.JunoAccessTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class ApiJunoTests {

    private final JunoAccessTokenService serviceAccessTokenJuno;

    @Autowired
    public ApiJunoTests(JunoAccessTokenService serviceAccessTokenJuno) {
        this.serviceAccessTokenJuno = serviceAccessTokenJuno;
    }

    @Test
    void testeToken() {
        serviceAccessTokenJuno.apiGerarNovoToken();
    }
}
