package com.pacoprojects.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.ItemNotaProduto} entity
 */
public record ItemNotaProdutoDto(

        Long id,

        @NotNull(message = "Quantidade de items obrigatório.")
        Double quantidade,

        @NotNull(message = "Produto deve ser informado.")
        ProdutoDtoBasicId produto,

        @NotNull(message = "Nota fiscal de compra deve ser informada.")
        NotaFiscalCompraDtoBasicId notaFiscalCompra,

        @NotNull(message = "Empresa deve ser informada.")
        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
