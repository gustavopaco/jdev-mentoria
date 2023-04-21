package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

import java.util.List;
import java.util.UUID;

public record ResponseWebHookReceberNotificacaoDto(

        UUID eventId,

        String eventType,

        String timestamp,

        List<ResponseWebHookReceberNotificacaoDataDto> data) {
}
