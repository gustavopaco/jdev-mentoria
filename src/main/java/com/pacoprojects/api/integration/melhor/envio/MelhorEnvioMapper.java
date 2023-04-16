package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.response.consulta.frete.MelhorEnvioConsultaFreteResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MelhorEnvioMapper {

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "valor", source = "price")
    @Mapping(target = "empresa", source = "company")
    MelhorEnvioConsultaFreteDto toEntity(MelhorEnvioConsultaFreteResponseDto melhorEnvioConsultaFreteResponseDto);
}
