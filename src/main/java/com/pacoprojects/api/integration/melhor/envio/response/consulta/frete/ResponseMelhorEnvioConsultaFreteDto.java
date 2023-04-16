package com.pacoprojects.api.integration.melhor.envio.response.consulta.frete;

import java.util.List;

public record ResponseMelhorEnvioConsultaFreteDto(

        Long id,
        String name,
        String error,
        String price,
        String custom_price,
        String discount,
        String currency,
        Integer delivery_time,
        Integer custom_delivery_time,
        ResponseConsultaFreteDeliveryRangeDto delivery_range,
        ResponseConsultaFreteDeliveryRangeDto custom_delivery_range,
        List<ResponseConsultaFretePackagesDto> packages,
        ResponseConsultaFreteAdditionalServicesDto additional_services,
        ResponseConsultaFreteCompanyDto company) { }

