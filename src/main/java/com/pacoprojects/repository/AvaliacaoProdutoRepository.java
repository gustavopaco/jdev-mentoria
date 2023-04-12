package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.AvaliacaoProdutoProjection;
import com.pacoprojects.model.AvaliacaoProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {

    List<AvaliacaoProdutoProjection> findAllByProduto_Id(Long idProduto);
    List<AvaliacaoProdutoProjection> findAllByPessoa_Id(Long idPessoa);
    List<AvaliacaoProdutoProjection> findAllByProduto_IdAndPessoa_Id(Long idProduto, Long idPessoa);


}
