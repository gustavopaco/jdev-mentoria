package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFretePurchaseDto(double discount, OffsetDateTime createdAt, List<Object> paypalDiscounts,
                                               List<ResponseCheckoutFreteTransactionDto> transactions, OffsetDateTime paidAt, String protocol, double total,
                                               OffsetDateTime updatedAt, List<ResponseCheckoutFreteOrderDto> orders, UUID id, String status) {
}
