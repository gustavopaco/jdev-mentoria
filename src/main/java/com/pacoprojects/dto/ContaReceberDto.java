package com.pacoprojects.dto;

import com.pacoprojects.enums.StatusContaReceber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.pacoprojects.model.ContaReceber} entity
 */
public record ContaReceberDto(Long id, @NotBlank(message = "Descrição obrigatório.") String descricao,
                              @NotNull(message = "Status da conta obrigatório.") StatusContaReceber status,
                              @NotNull(message = "Data de vencimento obrigatório.") LocalDate dataVencimento,
                              LocalDate dataPagamento,
                              @NotNull(message = "Valor total obrigatório.") BigDecimal valorTotal,
                              BigDecimal valorDesconto) implements Serializable {
}
