package com.pacoprojects.service;

import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
