package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

public record ResponseWebHookReceberNotificacaoChargeDto(

        String id,

        long code,

        String amount,

        String status,

        String dueDate,

        ResponseWebHookReceberNotificacaoPayerDto payer) {
}
