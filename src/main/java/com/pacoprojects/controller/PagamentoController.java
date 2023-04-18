package com.pacoprojects.controller;

import com.pacoprojects.api.integration.juno.ApiJunoBoletoService;
import com.pacoprojects.api.integration.juno.criar.cobranca.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.criar.cobranca.ResponseCobrancaJunoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final ApiJunoBoletoService apiJunoBoletoService;

    @PostMapping(path = "gerarBoleto")
    public ResponseEntity<ResponseCobrancaJunoDto> gerarBoletoPix(@Valid @RequestBody RequestCobrancaJunoDto cobrancaJunoDto) {
        return ResponseEntity.ok(apiJunoBoletoService.apiGerarBoleto(cobrancaJunoDto));
    }

    @DeleteMapping(path = "cancelarBoleto/{idBoleto}")
    public void cancelarBoleto(@PathVariable(name = "idBoleto") Long idBoleto) {
        apiJunoBoletoService.apiCancelarBoleto(idBoleto);
    }
}
