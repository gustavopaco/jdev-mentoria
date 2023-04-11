package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.PessoaJuridicaProjection;
import com.pacoprojects.model.PessoaJuridica;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    boolean existsPessoaJuridicaByCnpj(String cnpj);

    boolean existsPessoaJuridicaByInscricaoEstadual(String inscricaoEstadual);

    @EntityGraph(attributePaths = {"enderecos", "telefones", "contasReceber"})
    Optional<PessoaJuridicaProjection> findByCnpj(String cnpj);

    List<PessoaJuridicaProjection> findAllByNomeContainsIgnoreCase(String nome);
}
