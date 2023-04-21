package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

public record ResponseWebHookReceberNotificacaoDataDto(

        String entityId,

        String entityType,

        ResponseWebHookReceberNotificacaoAttributesDto attributes) {
}
