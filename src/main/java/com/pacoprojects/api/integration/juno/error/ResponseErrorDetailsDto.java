package com.pacoprojects.api.integration.juno.error;

public record ResponseErrorDetailsDto(
        String field,
        String message,
        String errorCode) {
}
