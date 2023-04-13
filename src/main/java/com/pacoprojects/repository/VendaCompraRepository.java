package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.VendaCompraProjectionSelected;
import com.pacoprojects.model.VendaCompra;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface VendaCompraRepository extends JpaRepository<VendaCompra, Long> {

    Optional<VendaCompraProjectionSelected> findVendaCompraById(Long id);


}
