package com.pacoprojects.repository;

import com.pacoprojects.model.VendaCompra;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface VendaCompraRepository extends JpaRepository<VendaCompra, Long> {


}
