package com.pacoprojects.api.integration.melhor.envio.checkout.frete.response;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFretePhoneDto(OffsetDateTime updatedAt, String phone, OffsetDateTime createdAt, long id, String label,
                                            String type, String countryid) {
}
