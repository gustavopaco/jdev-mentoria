package com.pacoprojects.dto.projections;

import com.pacoprojects.model.StatusRastreio;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDate;

/**
 * A Projection for the {@link com.pacoprojects.model.StatusRastreio} entity
 */
@Projection(name = "statusRastreioProjection", types = StatusRastreio.class)
public interface StatusRastreioProjection {
    Long getId();

    String getCentroDistribuicao();

    String getCidade();

    String getEstado();

    String getStatus();

    VendaCompraInfo getVendaCompra();

    /**
     * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
     */
    interface VendaCompraInfo {
        Long getId();

        Integer getDiasParaEntrega();

        LocalDate getDataVenda();

        LocalDate getDataEntrega();
    }
}
