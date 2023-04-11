package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ProdutoProjections;
import com.pacoprojects.model.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long id);

    List<ProdutoProjections> findAllByEmpresa_Id(Long idEmpresa);

    List<ProdutoProjections> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);

    Optional<ProdutoProjections> findProdutoById(Long id);
}
