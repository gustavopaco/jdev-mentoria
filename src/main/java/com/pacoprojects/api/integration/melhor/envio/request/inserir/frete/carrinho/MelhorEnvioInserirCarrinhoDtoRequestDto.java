package com.pacoprojects.api.integration.melhor.envio.request.inserir.frete.carrinho;

import lombok.Builder;

import java.util.List;

@Builder
public record MelhorEnvioInserirCarrinhoDtoRequestDto(

        Integer service,
        Integer agency,
        FromToMeDto from,
        FromToMeDto to,
        List<ProdutosMeDto> products,
        List<VolumesMeDto> volumes,
        OptionsMeDto options

) { }


