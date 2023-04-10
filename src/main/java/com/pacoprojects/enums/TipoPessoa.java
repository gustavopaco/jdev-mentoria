package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPessoa {

    FISICA("ROLE_FISICA"),
    JURIDICA("ROLE_JURIDICA"),
    ADMIN("ROLE_ADMIN");

    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
