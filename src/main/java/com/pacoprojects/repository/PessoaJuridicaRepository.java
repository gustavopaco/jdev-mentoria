package com.pacoprojects.repository;

import com.pacoprojects.model.PessoaJuridica;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    boolean existsPessoaJuridicaByCnpj(String cnpj);

    @EntityGraph(attributePaths = {"enderecos", "telefones", "contasReceber"})
    Optional<PessoaJuridica> findPessoaJuridicaByCnpj(String cnpj);

    boolean existsPessoaJuridicaByInscricaoEstadual(String inscricaoEstadual);
}
