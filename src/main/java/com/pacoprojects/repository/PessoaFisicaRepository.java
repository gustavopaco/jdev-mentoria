package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.PessoaFisicaProjection;
import com.pacoprojects.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    boolean existsPessoaFisicaByCpf(String cpf);

    List<PessoaFisicaProjection> findAllByNomeContainsIgnoreCase(String nome);

    Optional<PessoaFisicaProjection> findByCpf(String cpf);
}
