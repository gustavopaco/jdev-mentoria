package com.pacoprojects.repository;

import com.pacoprojects.model.BoletoJuno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {

    Optional<BoletoJuno> findByIdChrBoleto(String idChrBoleto);

    @Modifying
    @Query(value = "UPDATE BoletoJuno b SET b.quitado = true WHERE b.id =:id ")
    void updateBoletoStatusQuitadoTrue(@Param("id") Long  id);

}
