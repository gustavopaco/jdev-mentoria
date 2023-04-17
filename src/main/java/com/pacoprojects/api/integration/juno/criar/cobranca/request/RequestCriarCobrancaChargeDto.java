package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record RequestCriarCobrancaChargeDto(

        long maxOverdueDays,

        BigDecimal amount,

        long discountDays,

        List<String> references,

        String feeSchemaToken,

        String dueDate,

        boolean paymentAdvance,

        String description,

        String discountAmount,

        boolean pixIncludeImage,

        List<RequestCriarCobrancaSplitDto> split,

        long installments,

        BigDecimal fine,

        String interest,

        List<String> paymentTypes,

        String pixKey) {
}
