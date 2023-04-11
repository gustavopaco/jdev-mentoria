package com.pacoprojects.repository;

import com.pacoprojects.model.Pessoa;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
