package com.pacoprojects.repository;

import com.pacoprojects.model.Categoria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNomeIgnoreCaseAndEmpresa_Id(String nome, Long id);

    List<Categoria> findAllByEmpresa_Id(Long idEmpresa);

    List<Categoria> findAllByNomeContainsIgnoreCaseAndEmpresa_Id(String nome, Long idEmpresa);
}
