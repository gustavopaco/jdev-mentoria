package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record AgencyMeDto(String code, AddressMeDto address, long companyid, String initials, OffsetDateTime createdAt,
                          OffsetDateTime updatedAt, PhoneMeDto phone, String companyName, String name, long id, String email,
                          String status) {
}
