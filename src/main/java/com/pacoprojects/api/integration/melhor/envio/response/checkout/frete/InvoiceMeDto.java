package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record InvoiceMeDto(String number, String serie, String model, OffsetDateTime issuedAt, String key) {
}
