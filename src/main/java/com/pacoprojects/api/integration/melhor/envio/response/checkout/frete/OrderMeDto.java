package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record OrderMeDto(double discount, String protocol, double quote, double price, FromToMeDto from, UUID id,
                         long agencyid,
                         String format, long deliveryMin, List<TagMeDto> tags, OffsetDateTime paidAt, long deliveryMax,
                         boolean receipt, boolean collect, String status, boolean ownHand, double billedWeight,
                         OffsetDateTime createdAt, List<ProductMeDto> products, long insuranceValue,
                         OffsetDateTime updatedAt,
                         long serviceid, boolean nonCommercial, AgencyMeDto agency, boolean reverse, ServiceMeDto service,
                         FromToMeDto to,
                         InvoiceMeDto invoice) {
}
