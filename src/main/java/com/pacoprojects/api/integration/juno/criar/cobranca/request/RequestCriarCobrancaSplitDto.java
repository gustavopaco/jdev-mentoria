package com.pacoprojects.api.integration.juno.criar.cobranca.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestCriarCobrancaSplitDto {
    private String recipientToken;
    private long amount;
    private boolean chargeFee;
    private boolean amountRemainder;
    private long percentage;

}
