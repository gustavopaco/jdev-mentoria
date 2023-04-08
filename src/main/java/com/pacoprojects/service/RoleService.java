package com.pacoprojects.service;

import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        roleRepository.delete(optionalRole.orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(400), "Role nao existe")));
    }

    public Role loadById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(400)));
    }

    public Set<Role> loadAllByAuthority(String authority) {
        return new HashSet<>(roleRepository.findRolesByAuthorityContainsIgnoreCase(authority));
    }
}
