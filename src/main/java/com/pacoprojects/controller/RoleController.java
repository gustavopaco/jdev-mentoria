package com.pacoprojects.controller;

import com.pacoprojects.dto.RoleDto;
import com.pacoprojects.model.Role;
import com.pacoprojects.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleDto> addRole(@Valid @RequestBody Role role) {
        return ResponseEntity.ok(roleService.addRole(role));
    }

    @DeleteMapping(path = "{id}")
    public void deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<RoleDto> loadById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(roleService.loadById(id));
    }

    @GetMapping(path = "authority")
    public ResponseEntity<Set<RoleDto>> loadAllByAuthority(@RequestParam(name = "authority") String authority) {
        return ResponseEntity.ok(roleService.loadAllByAuthority(authority));
    }
}
