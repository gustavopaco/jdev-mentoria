package com.pacoprojects.controller;

import com.pacoprojects.model.Role;
import com.pacoprojects.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.addRole(role));
    }

    @DeleteMapping(path = "{id}")
    public void deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
    }
}
