package com.pacoprojects.controller;

import com.pacoprojects.model.Role;
import com.pacoprojects.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }
}
