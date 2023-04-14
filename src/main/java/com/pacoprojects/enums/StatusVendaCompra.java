package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusVendaCompra {


    FINALIZADA("Finalizada"),
    CANCELADA("Cancelada"),
    ABANDONOU_CARRINHO("Abandonou Carrinho");


    private final String descricao;

    @Override
    public String toString() {
        return descricao;
    }
}
