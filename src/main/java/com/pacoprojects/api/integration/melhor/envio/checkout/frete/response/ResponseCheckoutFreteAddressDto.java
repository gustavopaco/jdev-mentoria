package com.pacoprojects.api.integration.melhor.envio.checkout.frete.response;

import java.time.OffsetDateTime;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFreteAddressDto(String address, ResponseCheckoutFreteCityDto city, double latitude, OffsetDateTime createdAt, String label,
                                              OffsetDateTime updatedAt, String district, long id, String postalCode, double longitude) {
}
