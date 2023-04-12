package com.pacoprojects.repository;

import com.pacoprojects.dto.projections.ImagemProdutoProjections;
import com.pacoprojects.model.ImagemProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {

    List<ImagemProdutoProjections> findAllByProduto_Id(Long id);








    void deleteAllByProduto_Id(Long id);
}
