package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusContaReceber {

    COBRANCA("Pagar"),
    VENCIDA("Vencida"),
    ACERTA("Aberta"),
    QUITADA("Quitada");

    private final String status;

    @Override
    public String toString() {
        return this.status;
    }
}
