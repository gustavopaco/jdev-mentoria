package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.FormaPagamentoProjection;
import com.pacoprojects.model.FormaPagamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    Optional<FormaPagamentoProjection> findFormaPagamentoById(Long id);

    boolean existsByDescricaoAndEmpresa_Id(String descricao, Long idEmpresa);
}
