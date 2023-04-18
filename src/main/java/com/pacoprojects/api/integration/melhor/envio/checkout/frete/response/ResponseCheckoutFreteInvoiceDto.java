package com.pacoprojects.api.integration.melhor.envio.checkout.frete.response;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFreteInvoiceDto(String number, String serie, String model, OffsetDateTime issuedAt, String key) {
}
