package com.pacoprojects.api.integration.melhor.envio.checkout.frete.response;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFreteAgencyDto(String code, ResponseCheckoutFreteAddressDto address, long companyid, String initials, OffsetDateTime createdAt,
                                             OffsetDateTime updatedAt, ResponseCheckoutFretePhoneDto phone, String companyName, String name, long id, String email,
                                             String status) {
}
