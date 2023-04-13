package com.pacoprojects.dto.projections;

import com.pacoprojects.model.VendaCompra;
import org.springframework.data.rest.core.config.Projection;

/**
 * A Projection for the {@link com.pacoprojects.model.VendaCompra} entity
 */
@Projection(name = "vendaCompraProjectionBasicId", types = VendaCompra.class)
public interface VendaCompraProjectionBasicId {
    Long getId();
}
