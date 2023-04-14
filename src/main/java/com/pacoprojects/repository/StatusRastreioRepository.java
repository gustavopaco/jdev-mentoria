package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.StatusRastreioProjection;
import com.pacoprojects.model.StatusRastreio;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {


    @Query(value = "select s from StatusRastreio s where s.vendaCompra.id =:idVenda and s.vendaCompra.enabled = true ")
    List<StatusRastreioProjection> queryAllVendaCompraById(@Param("idVenda") Long idVenda);
    List<StatusRastreioProjection> findAllByVendaCompra_IdAndVendaCompra_Enabled(Long idVenda, boolean enabled, Sort sort);
}
