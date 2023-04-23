package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPagamento {

    CREDIT_CARD("CREDIT_CARD"),
    BOLETO_PIX("BOLETO_PIX");

    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
