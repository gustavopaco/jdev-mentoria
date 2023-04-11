package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.NotaFiscalCompraProjections;
import com.pacoprojects.model.NotaFiscalCompra;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {

    boolean existsByDescricaoIgnoreCaseAndPessoa_Id(String descricao, Long id);

    List<NotaFiscalCompraProjections> findAllByDescricaoContainsIgnoreCase(String descricao);

    List<NotaFiscalCompraProjections> findAllByPessoa_Id(Long idPessoa);

    List<NotaFiscalCompraProjections> findAllByContaPagar_Id(Long idContaPagar);

    List<NotaFiscalCompraProjections> findAllByEmpresa_Id(Long idEmpresa);

    Optional<NotaFiscalCompraProjections> findNotaFiscalCompraById(Long id);

}
