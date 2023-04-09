package com.pacoprojects.repository;

import com.pacoprojects.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUsername(String username);

    Optional<Usuario> findUsuarioByUsernameOrPessoa_Id(String username, Long id);

    Set<Usuario> findUsuariosByDateLastPasswordChangeBefore(LocalDateTime diasEmAtraso);
}
