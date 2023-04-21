package com.pacoprojects.api.integration.juno.webhook.receber.notificacao;

public record ResponseWebHookReceberNotificacaoAddressDto(

        String street,

        String number,

        String complement,

        String city,

        String state,

        String postCode,

        String neighborhood) {
}
