package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.response.MelhorEnvioDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MelhorEnvioMapper {

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "valor", source = "price")
    @Mapping(target = "empresa", source = "company")
    MelhorEnvioDto toEntity(MelhorEnvioDtoResponse melhorEnvioDtoResponse);
}
