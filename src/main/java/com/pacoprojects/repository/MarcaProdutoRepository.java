package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.MarcaProdutoProjections;
import com.pacoprojects.model.MarcaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);

    List<MarcaProdutoProjections> findAllByEmpresa_Id(Long idEmpresa);

    Optional<MarcaProdutoProjections> findMarcaProdutoById(Long id);

    List<MarcaProdutoProjections> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);
}
