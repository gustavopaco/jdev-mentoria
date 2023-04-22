package com.pacoprojects.api.integration.juno;

import com.pacoprojects.api.integration.juno.webhook.criar.request.RequestJunoCriarWebHook;
import com.pacoprojects.api.integration.juno.webhook.criar.response.ResponseJunoCriarWebHookDto;
import com.pacoprojects.api.integration.juno.webhook.receber.notificacao.ResponseJunoWebHookReceberNotificacaoDto;
import com.pacoprojects.api.integration.juno.webhook.receber.notificacao.ResponseWebHookReceberNotificacaoDataDto;
import com.pacoprojects.model.AccessTokenJuno;
import com.pacoprojects.model.BoletoJuno;
import com.pacoprojects.repository.BoletoJunoRepository;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiJunoWebHookService {

    private final BoletoJunoRepository repositoryBoletoJuno;
    private final JunoAccessTokenService accessTokenService;
    private final ApplicationConfig applicationConfig;
    private final JunoConfig junoConfig;


    public void apiReceberNotificacaoPagamento(ResponseJunoWebHookReceberNotificacaoDto notificacaoDto, HttpHeaders headers) {

        if (headers != null && headers.getFirst("X-Signature") != null) {
            System.out.println("O código Hash enviado pelo header é: " + headers.getFirst("X-Signature"));
            //TODO Se o codigo hash vindo pelo Header for igual ao que deveria estar gravado no banco, entao vamos liberar o evento de mudanca de status de pagamento
        }

        for (ResponseWebHookReceberNotificacaoDataDto dataDto : notificacaoDto.data()) {
            boolean isBoletoPago = dataDto.attributes().status().equals("CONFIRMED");
            Optional<BoletoJuno> optionalBoletoJuno = repositoryBoletoJuno.findByIdChrBoleto(dataDto.attributes().charge().id());

            if (optionalBoletoJuno.isPresent()) {
                if (!optionalBoletoJuno.get().isQuitado() && isBoletoPago) {
                    repositoryBoletoJuno.updateBoletoStatusQuitadoTrue(optionalBoletoJuno.get().getId());
                    // TODO Enviar emails notificando cliente e empresa, mudar o status da venda
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void apiCriarWebHook(RequestJunoCriarWebHook webHookCriar) {

        AccessTokenJuno accessTokenJuno = accessTokenService.apiGerarNovoToken();

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        HttpEntity<RequestJunoCriarWebHook> bodyHeaders = new HttpEntity<>(webHookCriar, headers);


        ResponseEntity<ResponseJunoCriarWebHookDto> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlWebHook(), HttpMethod.POST, bodyHeaders, ResponseJunoCriarWebHookDto.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar Webhook.");
        }

        // TODO Apos criar um webhook, devemos salvar no banco de dados o "id" do webHook e tambem a chave "secret"
        // Json de exemplo: {"_embedded":{"webhooks":[{"id":"wbh_B4CDBA74B4859D09","url":"https://api.jdev.mentoria.pacoprojects.com/api/jdev-mentoria/pagamento/webhook","secret":"0a443e1554f67176728a8c73eed0229d5ee7336038eb1b5d52d9c06d1716ca2b","status":"ACTIVE","eventTypes":[{"id":"evt_D48AFD95E13CA227","name":"BILL_PAYMENT_STATUS_CHANGED","label":"O status de um pagamento de contas foi alterado","status":"ENABLED"},{"id":"evt_3891C9EEE7F6CC9A","name":"PAYMENT_NOTIFICATION","label":"Uma notificação de pagamento foi gerada","status":"ENABLED"},{"id":"evt_CFCECF82F4BBED68","name":"CHARGE_STATUS_CHANGED","label":"O status de uma cobrança foi alterado","status":"ENABLED"}],"_links":{"self":{"href":"https://sandbox.boletobancario.com/api-integration/notifications/webhooks/wbh_B4CDBA74B4859D09"}}}]},"_links":{"self":{"href":"https://sandbox.boletobancario.com/api-integration/notifications/webhooks"}}}

        System.out.println(response.getBody());
    }

    public void apiListarWebHooks() {

        AccessTokenJuno accessTokenJuno = accessTokenService.apiGerarNovoToken();

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        HttpEntity<Object> bodyHeaders = new HttpEntity<>(headers);

        ResponseEntity<String> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlWebHook(), HttpMethod.GET, bodyHeaders, String.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível conectar a Api de pagamento");
        }

        System.out.println(response.getBody());
    }

    public void deletarWebHook() {

        AccessTokenJuno accessTokenJuno = accessTokenService.apiGerarNovoToken();

        HttpHeaders headers = junoConfig.getDefaultHeaders(accessTokenJuno.getAccess_token());

        HttpEntity<Object> bodyHeaders = new HttpEntity<>(headers);

        ResponseEntity<Void> response = applicationConfig
                .getRestTemplateInstance()
                .exchange(junoConfig.urlDeletarWebHook("wbh_B88B91184CA3F2C0"), HttpMethod.DELETE, bodyHeaders, Void.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível conectar a Api de pagamento");
        }
    }
}
