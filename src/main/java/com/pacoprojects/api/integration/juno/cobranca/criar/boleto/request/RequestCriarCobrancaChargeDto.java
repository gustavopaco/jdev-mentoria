package com.pacoprojects.api.integration.juno.cobranca.criar.boleto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class RequestCriarCobrancaChargeDto {
    private long maxOverdueDays;
    private BigDecimal amount;
    private long discountDays;
    private List<String> references;
    private String feeSchemaToken;
    private String dueDate;
    private boolean paymentAdvance;
    private String description;
    private String discountAmount;
    private boolean pixIncludeImage;
    private List<RequestCriarCobrancaSplitDto> split;
    private long installments;
    private BigDecimal fine;
    private String interest;
    private List<String> paymentTypes;
    private String pixKey;

}
