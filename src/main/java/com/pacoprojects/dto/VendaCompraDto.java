package com.pacoprojects.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.pacoprojects.model.VendaCompra} entity
 */
public record VendaCompraDto(

        Long id,

        @NotNull(message = "Valor total obrigatório.")
        @Min(value = 1, message = "Valor total inválido")
        BigDecimal valorTotal,

        BigDecimal valorDesconto,

        @NotNull(message = "Valor do frete obrigatório.")
        BigDecimal valorFrete,

        @Min(value = 1, message = "Quantidade de dias para entrega inválido")
        Integer diasParaEntrega,

        LocalDate dataVenda,

        LocalDate dataEntrega,

        @NotNull(message = "Pessoa Física deve ser informada.")
        PessoaFisicaDto pessoa,

        @NotNull(message = "Endereço de entrega deve ser informado.")
        EnderecoDto enderecoEntrega,

        @NotNull(message = "Endereço de cobrança deve ser informado.")
        EnderecoDto enderecoCobranca,

        @NotNull(message = "Forma de pagamento deve ser informado.")
        FormaPagamentoDtoBasicId formaPagamento,


        NotaFiscalVendaDto notaFiscalVenda,

        CupomDescontoDtoBasicId cupomDesconto,

        @NotNull(message = "Empresa deve ser informada.")
        PessoaJuridicaDtoBasicId empresa

) implements Serializable {
}
