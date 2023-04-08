package com.pacoprojects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacoprojects.controller.RoleController;
import com.pacoprojects.model.Role;
import com.pacoprojects.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles(value = "dev")
public class RoleTests {

    private final RoleController controllerRole;
    private final RoleRepository repositoryRole;
    private final WebApplicationContext wac;

    @Autowired
    public RoleTests(RoleController controllerRole, RoleRepository repositoryRole, WebApplicationContext wac) {
        this.controllerRole = controllerRole;
        this.repositoryRole = repositoryRole;
        this.wac = wac;
    }


    @Test
    void addRole() {
        Role role = new Role();
        role.setAuthority("ROLE_JUNIOR");

        assertNull(role.getId());

        ResponseEntity<Role> response = controllerRole.addRole(role);
        role = response.getBody();

        // Teste Status 200
        assertEquals(200, response.getStatusCode().value());

        // Teste Save
        assertNotNull(role);
        assertTrue(role.getId() > 0);
        assertEquals("ROLE_JUNIOR", role.getAuthority());


        Optional<Role> optionalRole = repositoryRole.findById(role.getId());
        // Teste LoadById
        assertTrue(optionalRole.isPresent());
        assertEquals(role.getId(), optionalRole.get().getId());

        repositoryRole.deleteById(optionalRole.get().getId());
        Role role1 = repositoryRole.findById(optionalRole.get().getId()).orElse(null);
        // Teste Delete
        assertNull(role1);


        role = new Role();
        role.setAuthority("ROLE_TESTE");
        ResponseEntity<Role> response2 = controllerRole.addRole(role);
        List<Role> roles = repositoryRole.findRolesByAuthorityContainsIgnoreCase("TESTE");
        // Teste Query
        assertEquals(1, roles.size());
        repositoryRole.deleteById(Objects.requireNonNull(response2.getBody()).getId());

    }

    @Test
    void loadRoles() {
        List<Role> roles = repositoryRole.findAll();
        assertTrue(roles.size() > 0);
    }

    @Test
    void testRestApiAddRole() throws Exception {
        // Teste de Endpoint - ADD
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        MockMvc mockMvc = builder.build();

        Role role = new Role();
        role.setAuthority("ROLE_COMPRADOR");

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                // Endpoint
                .perform(MockMvcRequestBuilders.post("/roles")
                        // Objeto do post
                        .content(objectMapper.writeValueAsString(role))
                        // Tipo de envio
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // Converter retorno da Api para Objeto role;
        System.out.println("Retorno da Api:" + retornoApi.andReturn().getResponse().getContentAsString());

        Role role1 = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Role.class);

        assertNotNull(role1);
        assertEquals(role.getAuthority(), role1.getAuthority());

        repositoryRole.deleteById(role1.getId());
    }

    @Test
    void testeRestApiDeleteRole() throws Exception {
        // Teste de Endpoint - ADD
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        MockMvc mockMvc = builder.build();

        Role role = new Role();
        role.setAuthority("ROLE_COMPRADOR");

        Role roleSaved = repositoryRole.save(role);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                // Endpoint
                .perform(MockMvcRequestBuilders.delete("/roles/" + roleSaved.getId())
                        // Tipo de envio
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

        repositoryRole.deleteById(roleSaved.getId());
    }

    @Test
    void loadById() throws Exception {
        // Teste de Endpoint - ADD
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        MockMvc mockMvc = builder.build();

        Role role = new Role();
        role.setAuthority("ROLE_SUPORTE");

        Role roleSaved = repositoryRole.save(role);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                // Endpoint
                .perform(MockMvcRequestBuilders.get("/roles/" + roleSaved.getId())
                        .content(objectMapper.writeValueAsString(roleSaved))
                        // Tipo de envio
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));


        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
        Role role1 = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Role.class);
        assertNotNull(role1);
        assertEquals(role.getAuthority(), role1.getAuthority());
        assertEquals(roleSaved.getId(), role1.getId());

        repositoryRole.deleteById(role1.getId());
    }

    @Test
    void loadAllByAuthority() throws Exception {
        // Teste de Endpoint - ADD
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        MockMvc mockMvc = builder.build();

        Role role = new Role();
        role.setAuthority("ROLE_VENDEDOR");

        Role roleSaved = repositoryRole.save(role);

        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions retornoApi = mockMvc
                // Endpoint
                .perform(MockMvcRequestBuilders.get("/roles/authority")
                        .param("authority", roleSaved.getAuthority())
                        // Tipo de envio
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));


        assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
        List<Role> roles= objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(roleSaved.getId(), roles.get(0).getId());
        assertEquals(role.getAuthority(), roles.get(0).getAuthority());

        // Deletando apos passar nos testes
        repositoryRole.deleteAll(roles);
    }

}
