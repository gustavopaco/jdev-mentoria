package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record PhoneMeDto(OffsetDateTime updatedAt, String phone, OffsetDateTime createdAt, long id, String label,
                         String type, String countryid) {
}
