package com.pacoprojects.repository;

import com.pacoprojects.model.CupomDesconto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {

    List<CupomDesconto> findAllByEmpresa_Id(Long idEmpresa);

    Optional<CupomDesconto> findByCodigoDescricaoAndEmpresa_Id(String codDescricao, Long idEmpresa);

    boolean existsByCodigoDescricaoAndEmpresa_Id(String codDescricao, Long idEmpresa);

    boolean existsByCodigoDescricaoAndEmpresa_IdAndIdNot(String codigoDescricao, Long idEmpresa, Long idCupom);

    @Query(value = "select c from CupomDesconto c where c.codigoDescricao =:codigoDescricao and c.empresa.id =:idEmpresa and c.id <> :idCupom ")
    boolean existByDescricaoAndEmpresaIdAndIdNotEquals(@Param("codigoDescricao") String codigoDescricao,@Param("idEmpresa") Long idEmpresa, @Param("idCupom") Long idCupom);
}
