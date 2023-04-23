package com.pacoprojects.controller;

import com.pacoprojects.api.integration.juno.ApiJunoCobrancaService;
import com.pacoprojects.api.integration.juno.ApiJunoWebHookService;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.RequestCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.cobranca.criar.boleto.ResponseCobrancaJunoDto;
import com.pacoprojects.api.integration.juno.webhook.receber.notificacao.ResponseJunoWebHookReceberNotificacaoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final ApiJunoCobrancaService apiJunoCobrancaService;
    private final ApiJunoWebHookService apiJunoWebHookService;

    @PostMapping(path = "gerarBoleto")
    public ResponseEntity<ResponseCobrancaJunoDto> gerarBoletoPix(@Valid @RequestBody RequestCobrancaJunoDto cobrancaJunoDto) {
        return ResponseEntity.ok(apiJunoCobrancaService.apiGerarCobrancaBoleto(cobrancaJunoDto));
    }

    @DeleteMapping(path = "cancelarBoleto/{idBoleto}")
    public void cancelarBoleto(@PathVariable(name = "idBoleto") Long idBoleto) {
        apiJunoCobrancaService.apiCancelarBoleto(idBoleto);
    }

    @PostMapping(path = "webhook")
    public void receberNotificacaoPagamento(@RequestBody ResponseJunoWebHookReceberNotificacaoDto notificacaoDto, @RequestHeader HttpHeaders headers) {
        apiJunoWebHookService.apiReceberNotificacaoPagamento(notificacaoDto, headers);
    }
}
