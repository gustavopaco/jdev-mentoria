package com.pacoprojects.repository;

import com.pacoprojects.model.MarcaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long id);

    List<MarcaProduto> findAllByEmpresa_Id(Long idEmpresa);

    List<MarcaProduto> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);
}
