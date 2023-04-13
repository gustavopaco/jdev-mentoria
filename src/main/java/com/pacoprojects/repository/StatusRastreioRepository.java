package com.pacoprojects.repository;

import com.pacoprojects.model.StatusRastreio;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {
}
