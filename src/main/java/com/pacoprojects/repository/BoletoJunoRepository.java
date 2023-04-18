package com.pacoprojects.repository;

import com.pacoprojects.model.BoletoJuno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {

    Optional<BoletoJuno> findByIdChrBoleto(String idChrBoleto);
}
