package com.pacoprojects.api.integration.melhor.envio.response.checkout.frete;

import java.time.OffsetDateTime;
import java.util.UUID;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFreteTransactionDto(ResponseCheckoutFreteReasonDto reason, String description, OffsetDateTime createdAt, String type,
                                                  OffsetDateTime authorizedAt, String protocol, UUID id, double value, String status) {
}
