package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

public record ResponseWebHookReceberNotificacaoPayerDto(

        String name,

        String document,

        ResponseWebHookReceberNotificacaoAddressDto address,

        String id) {
}
