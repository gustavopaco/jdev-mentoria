package com.pacoprojects.dto.projections;

import com.pacoprojects.enums.StatusContaReceber;
import com.pacoprojects.model.ContaReceber;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.ContaReceber} entity
 */
@Projection(name = "contaReceberProjection", types = ContaReceber.class)
public interface ContaReceberProjection {
    Long getId();

    String getDescricao();

    StatusContaReceber getStatus();

    LocalDate getDataVencimento();

    LocalDate getDataPagamento();

    BigDecimal getValorTotal();

    BigDecimal getValorDesconto();
}
