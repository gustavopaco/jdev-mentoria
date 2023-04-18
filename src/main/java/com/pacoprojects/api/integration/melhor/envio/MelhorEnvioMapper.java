package com.pacoprojects.api.integration.melhor.envio;

import com.pacoprojects.api.integration.melhor.envio.consulta.frete.response.ResponseMelhorEnvioConsultaFreteDto;
import com.pacoprojects.api.integration.melhor.envio.inserir.frete.carrinho.response.ResponseMelhorEnvioInserirFreteCarrinhoDto;
import com.pacoprojects.model.VendaCompra;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MelhorEnvioMapper {

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "valor", source = "price")
    @Mapping(target = "empresa", source = "company")
    MelhorEnvioConsultaFreteDto toDto(ResponseMelhorEnvioConsultaFreteDto responseMelhorEnvioConsultaFreteDto);

    @Mapping(target = "codigoEtiqueta", source = "id")
    MelhorEnvioInserirFreteDto toDto(ResponseMelhorEnvioInserirFreteCarrinhoDto responseMelhorEnvioInserirFreteCarrinhoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VendaCompra partialUpdate(MelhorEnvioInserirFreteDto melhorEnvioInserirFreteDto, @MappingTarget VendaCompra vendaCompra);
}
