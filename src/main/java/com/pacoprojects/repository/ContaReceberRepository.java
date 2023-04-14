package com.pacoprojects.repository;

import com.pacoprojects.model.ContaReceber;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {
}
