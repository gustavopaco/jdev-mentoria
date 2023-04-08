package com.pacoprojects;

import com.pacoprojects.controller.RoleController;
import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(value = "dev")
public class RoleTests{

    private final RoleController roleController;
    private final RoleRepository repositoryRole;

    @Autowired
    public RoleTests(RoleController roleController, RoleRepository repositoryRole) {
        this.roleController = roleController;
        this.repositoryRole = repositoryRole;
    }


    @Test
    void addRole() {
        Role role = new Role();
        role.setAuthority("ROLE_JUNIOR");

        assertNull(role.getId());

        ResponseEntity<Role> response = roleController.addRole(role);
        role = response.getBody();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(role);
        assertTrue(role.getId() > 0);
        assertEquals("ROLE_JUNIOR", role.getAuthority());
        Optional<Role> optionalRole = repositoryRole.findById(role.getId());
        assertTrue(optionalRole.isPresent());
        assertEquals(role.getId(), optionalRole.get().getId());

        // Teste Delete
        repositoryRole.deleteById(optionalRole.get().getId());
        Role role1 = repositoryRole.findById(optionalRole.get().getId()).orElse(null);
        assertNull(role1);

        // Teste Query
        role = new Role();
        role.setAuthority("ROLE_TESTE");

        ResponseEntity<Role> response2 = roleController.addRole(role);

        List<Role> roles = repositoryRole.findRolesByAuthorityContainsIgnoreCase("TESTE");
        assertEquals(1 , roles.size());
        repositoryRole.deleteById(Objects.requireNonNull(response2.getBody()).getId());

    }

    @Test
    void loadRoles() {
        List<Role> roles = repositoryRole.findAll();
        assertTrue(roles.size() > 0);
    }

    @Test
    void deleteRole() {
    }
}
