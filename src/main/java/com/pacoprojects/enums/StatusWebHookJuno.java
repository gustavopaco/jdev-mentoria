package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusWebHookJuno {

    PRECONFIRMED("PRECONFIRMED"),
    CONFIRMED("CONFIRMED"),
    NOT_AUTHORIZED("NOT_AUTHORIZED"),
    FAILED("FAILED");


    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
