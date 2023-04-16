package com.pacoprojects.api.integration.melhor.envio.response.consulta.frete;

import java.util.List;

public record MelhorEnvioConsultaFreteResponseDto(

        Long id,
        String name,
        String error,
        String price,
        String custom_price,
        String discount,
        String currency,
        Integer delivery_time,
        Integer custom_delivery_time,
        DeliveryRangeMeDto delivery_range,
        DeliveryRangeMeDto custom_delivery_range,
        List<PackagesMeDto> packages,
        AdditionalServicesMeDto additional_services,
        CompanyMeDto company) { }

