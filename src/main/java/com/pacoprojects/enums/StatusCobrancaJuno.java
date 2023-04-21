package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCobrancaJuno {

    ACTIVE("ACTIVE"),
    CANCELLED("CANCELLED"),
    MANUAL_RECONCILIATION("MANUAL_RECONCILIATION"),
    FAILED("FAILED"),
    PAID("PAID");

    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
