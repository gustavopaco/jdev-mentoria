package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.NotaFiscalVendaProjection;
import com.pacoprojects.model.NotaFiscalVenda;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface NotaFiscalVendaRepository extends JpaRepository<NotaFiscalVenda, Long> {

    @Query(value = "select n from NotaFiscalVenda n where n.vendaCompra.id =:idVenda and n.vendaCompra.enabled =:enabled ")
    Optional<NotaFiscalVendaProjection> queryVendaCompraByIdAndEnabledTrue(@Param("idVenda") Long idVenda, @Param("enabled") boolean enabled);

    Optional<NotaFiscalVendaProjection> findByVendaCompra_IdAndVendaCompra_Enabled(Long idVendaCompra, boolean enabled);
}
