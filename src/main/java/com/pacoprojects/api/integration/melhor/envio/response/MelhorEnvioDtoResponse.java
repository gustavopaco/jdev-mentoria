package com.pacoprojects.api.integration.melhor.envio.response;

import java.util.List;

public record MelhorEnvioDtoResponse(

        Long id,
        String name,
        String error,
        String price,
        String custom_price,
        String discount,
        String currency,
        Integer delivery_time,
        Integer custom_delivery_time,
        DeliveryRange delivery_range,
        DeliveryRange custom_delivery_range,
        List<Packages> packages,
        AdditionalServices additional_services,
        CompanyResponse company) { }

record DeliveryRange(Integer min, Integer max) { }
record Packages(String price, String discount, String format, String weight, String insurance_value, PackagesDimensions dimensions, List<PackagesProdutos> products) { }
    record PackagesDimensions(Integer height, Integer length, Integer width) { }
    record PackagesProdutos(String id, Integer quantity) { }
record AdditionalServices(Boolean collect, Boolean own_hand, Boolean receipt) { }
