package com.pacoprojects.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pacoprojects.model.NotaFiscalVenda} entity
 */
public record NotaFiscalVendaDto(

        Long id,

        @NotBlank(message = "Número da nota obrigatório.")
        String numero,

        @NotBlank(message = "Série da nota obrigatório.")
        String serie,

        @NotBlank(message = "Tipo da nota obrigatório.")
        String tipo,

        @NotBlank(message = "Xml da nota obrigatório.")
        String xml,

        @NotBlank(message = "Pdf da nota obrigatório.")
        String pdf,

        VendaCompraDtoBasicId vendaCompra,

        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
