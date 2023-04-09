package com.pacoprojects.service;

import com.pacoprojects.dto.RoleDto;
import com.pacoprojects.mapper.RoleMapper;
import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repositoryRole;
    private final RoleMapper mapperRole;

    public RoleDto addRole(Role role) {

        Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase(role.getAuthority());

        if (optionalRole.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Está permissão já existe");
        }

        return mapperRole.toDto(repositoryRole.save(role));
    }

    public void deleteRole(Long id) {
        Optional<Role> optionalRole = repositoryRole.findById(id);
        repositoryRole.delete(optionalRole.orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(400), "Role nao existe")));
    }

    public RoleDto loadById(Long id) {
        return mapperRole.toDto(repositoryRole.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permissão não foi encontrada")));
    }

    public Set<RoleDto> loadAllByAuthority(String authority) {
        return repositoryRole.findRolesByAuthorityContainsIgnoreCase(authority)
                .stream().map(mapperRole::toDto).collect(Collectors.toSet());
    }
}
