package com.pacoprojects.repository;

import com.pacoprojects.model.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByAuthorityContainsIgnoreCase(String authority);
    List<Role> findRolesByAuthorityContainsIgnoreCase(String authority);
}
