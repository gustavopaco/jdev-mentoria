package com.pacoprojects.mapper;

import com.pacoprojects.dto.projections.ContaPagarProjections;
import com.pacoprojects.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

    boolean existsByDescricaoContainsIgnoreCaseAndPessoa_Id(String descricao, Long idPessoa);

    List<ContaPagarProjections> findAllByDescricaoContainsIgnoreCase(String descricao);

    List<ContaPagarProjections> findAllByDescricaoContainsIgnoreCaseAndPessoa_Id(String descricao, Long idPessoa);

    List<ContaPagarProjections> findAllByDescricaoContainsIgnoreCaseAndPessoaFornecedor_Id(String descricao, Long idFornecedor);

    List<ContaPagarProjections> findAllByDescricaoContainsIgnoreCaseAndEmpresa_Id(String descricao, Long idEmpresa);

    List<ContaPagarProjections> findAllByEmpresa_Id(Long id);
}
