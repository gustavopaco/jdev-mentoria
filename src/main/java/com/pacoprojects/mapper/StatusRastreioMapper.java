package com.pacoprojects.mapper;

import com.pacoprojects.api.integration.melhor.envio.response.rastreio.pedido.ResponseMelhorEnvioRastreioPedidoDto;
import com.pacoprojects.model.StatusRastreio;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatusRastreioMapper {

    ResponseMelhorEnvioRastreioPedidoDto toDto(StatusRastreio statusRastreio);
}
