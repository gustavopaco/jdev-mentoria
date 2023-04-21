package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

public record ResponseWebHookReceberNotificacaoAttributesDto(

        String createdOn,

        String date,

        String releaseDate,

        String amount,

        String fee,

        String status,

        String type,

        ResponseWebHookReceberNotificacaoChargeDto charge,

        ResponseWebHookReceberNotificacaoPixDto pix,

        String digitalAccountId) {
}
