package com.pacoprojects.repository;

import com.pacoprojects.model.ItemNotaProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ItemNotaProdutoRepository extends JpaRepository<ItemNotaProduto, Long> {

    @Modifying
    void deleteAllByNotaFiscalCompra_Id(Long id);
}
