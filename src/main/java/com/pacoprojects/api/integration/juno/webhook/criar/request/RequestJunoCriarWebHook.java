
package com.pacoprojects.api.integration.juno.webhook.criar.request;

import lombok.Builder;

import java.util.List;

@Builder
public record RequestJunoCriarWebHook(String url, List<String> eventTypes) {
}
