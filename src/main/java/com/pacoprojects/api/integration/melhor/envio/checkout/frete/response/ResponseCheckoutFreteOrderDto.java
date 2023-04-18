package com.pacoprojects.api.integration.melhor.envio.checkout.frete.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

//YApi QuickType插件生成，具体参考文档:https://github.com/RmondJone/YapiQuickType
public record ResponseCheckoutFreteOrderDto(double discount, String protocol, double quote, double price, ResponseCheckoutFreteFromToDto from, UUID id,
                                            long agencyid,
                                            String format, long deliveryMin, List<ResponseCheckoutFreteTagDto> tags, OffsetDateTime paidAt, long deliveryMax,
                                            boolean receipt, boolean collect, String status, boolean ownHand, double billedWeight,
                                            OffsetDateTime createdAt, List<ResponseCheckoutFreteProductDto> products, long insuranceValue,
                                            OffsetDateTime updatedAt,
                                            long serviceid, boolean nonCommercial, ResponseCheckoutFreteAgencyDto agency, boolean reverse, ResponseCheckoutFreteServiceDto service,
                                            ResponseCheckoutFreteFromToDto to,
                                            ResponseCheckoutFreteInvoiceDto invoice) {
}
