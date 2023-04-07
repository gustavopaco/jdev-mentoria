package com.pacoprojects;

import com.pacoprojects.controller.RoleController;
import com.pacoprojects.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest()
@ActiveProfiles("dev")
class AppTests {

    private final RoleController roleController;

    @Autowired
    public AppTests(RoleController roleController) {
        this.roleController = roleController;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void addRole() {
        Role role = new Role();
        role.setAuthority("ROLE_JURIDICA");
        roleController.addRole(role);
    }
}
