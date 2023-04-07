package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoEndereco {

    COBRANCA("Cobrança"),
    ENTREGA("Entrega");

    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
