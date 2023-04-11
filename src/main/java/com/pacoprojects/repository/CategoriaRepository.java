package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.CategoriaProjections;
import com.pacoprojects.model.Categoria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long id);

    List<CategoriaProjections> findAllByEmpresa_Id(Long idEmpresa);

    List<CategoriaProjections> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);

    Optional<CategoriaProjections> findCategoriaById(Long id);
}
