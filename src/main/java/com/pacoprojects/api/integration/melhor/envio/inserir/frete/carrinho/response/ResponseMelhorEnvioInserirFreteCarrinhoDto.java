package com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.response;

import java.math.BigDecimal;
import java.util.List;

public record ResponseMelhorEnvioInserirFreteCarrinhoDto(

        String id,
        String protocol,
        Integer service_id,
        Integer agency_id,
        String contract,
        String service_code,
        BigDecimal quote,
        BigDecimal price,
        String coupon,
        BigDecimal discount,
        Integer delivery_min,
        Integer delivery_max,
        String status,
        String reminder,
        BigDecimal insurance_value,
        BigDecimal weight,
        Integer width,
        Integer height,
        Integer length,
        String diameter,
        String format,
        BigDecimal billed_weight,
        Boolean receipt,
        Boolean own_hand,
        Boolean collect,
        String collect_scheduled_at,
        Boolean reverse,
        Boolean non_commercial,
        String authorization_code,
        String tracking,
        String self_tracking,
        String delivery_receipt,
        String additional_info,
        String cte_key,
        String paid_at,
        String generated_at,
        String posted_at,
        String delivered_at,
        String canceled_at,
        String suspended_at,
        String expired_at,
        String created_at,
        String updated_at,
        String parse_pi_at,

        List<ResponseInserirFreteCarrinhoProdutosDto> products,
        List<ResponseInserirFreteCarrinhoVolumesDto> volumes,
        List<ResponseInserirFreteCarrinhoTagsDto> tags
) {
}
