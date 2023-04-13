package com.pacoprojects.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.ItemVendaCompra} entity
 */
public record ItemVendaCompraDto(

        Long id,

        @NotNull(message = "Quantidade de itens obrigatório.")
        Double quantidade,

        ProdutoDtoBasicId produto,

        VendaCompraDtoBasicId vendaCompra,

        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
