package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ItemNotaProdutoProjections;
import com.pacoprojects.model.ItemNotaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ItemNotaProdutoRepository extends JpaRepository<ItemNotaProduto, Long> {

    boolean existsByProduto_IdAndNotaFiscalCompra_Id(Long idProduto, Long idNotaFiscalCompra);


    List<ItemNotaProdutoProjections> findAllByProduto_Id(Long idProduto);

    List<ItemNotaProdutoProjections> findAllByNotaFiscalCompra_Id(Long idNotaFiscalCompra);

    List<ItemNotaProdutoProjections> findAllByEmpresa_Id(Long idEmpresa);

    Optional<ItemNotaProdutoProjections> findItemNotaProdutoById(Long id);

    @Modifying
    void deleteAllByNotaFiscalCompra_Id(Long id);


}
