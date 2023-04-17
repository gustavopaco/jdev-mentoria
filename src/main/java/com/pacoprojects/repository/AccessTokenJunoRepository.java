package com.pacoprojects.repository;

import com.pacoprojects.model.AccessTokenJuno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AccessTokenJunoRepository extends JpaRepository<AccessTokenJuno, Long> {
}
