package com.pacoprojects.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCartaoJuno {

    DECLINED,
    FAILED,
    NOT_AUTHORIZED,
    CONFIRMED,
    CUSTOMER_PAID_BACK,
    BANK_PAID_BACK,
    PARTIALLY_REFUNDED;

}
