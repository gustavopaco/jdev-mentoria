package com.pacoprojects.api.integration.juno.webhook.criar.response;

import java.util.List;

public record ResponseJunoCriarWebHookDto(

        String id,

        String url,

        String secret,

        String status,

        List<ResponseWebHookCriarEventTypeDto> eventTypes,

        ResponseWebHookCriarLinkDto _links) {
}


