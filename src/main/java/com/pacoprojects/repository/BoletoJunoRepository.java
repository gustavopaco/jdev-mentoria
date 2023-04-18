package com.pacoprojects.repository;

import com.pacoprojects.model.BoletoJuno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BoletoJunoRepository extends JpaRepository<BoletoJuno, Long> {
}
