package com.pacoprojects.repository;

import com.pacoprojects.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    boolean existsPessoaJuridicaByCnpj(String cnpj);
}
