package com.pacoprojects.service;

import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.email.EmailMessage;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.mapper.PessoaJuridicaMapper;
import com.pacoprojects.model.PessoaJuridica;
import com.pacoprojects.model.Role;
import com.pacoprojects.model.Usuario;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import com.pacoprojects.repository.RoleRepository;
import com.pacoprojects.repository.UsuarioRepository;
import com.pacoprojects.security.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaUserService {

    private final PessoaJuridicaRepository repositoryJuridica;
    private final UsuarioRepository repositoryUsuario;
    private final RoleRepository repositoryRole;
    private final ApplicationConfig applicationConfig;
    private final PessoaJuridicaMapper mapperJuridica;
    private final EmailService serviceEmail;

    public RegisterPessoaJuridicaDto addPessoaJuridica(RegisterPessoaJuridicaDto pessoaJuridica) {

        if (pessoaJuridica == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não pode ser nula");
        }

        if (pessoaJuridica.id() == null && repositoryJuridica.existsPessoaJuridicaByCnpj(pessoaJuridica.cnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa registrada com esse Cnpj: " + pessoaJuridica.cnpj());
        }

        if (pessoaJuridica.id() == null && repositoryJuridica.existsPessoaJuridicaByInscricaoEstadual(pessoaJuridica.inscricaoEstadual())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa registrada com essa Inscrição Estadual.");
        }

        Optional<Usuario> optionalUsuario = repositoryUsuario.findUsuarioByUsername(pessoaJuridica.email());

        if (optionalUsuario.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já está em uso");
        }

        PessoaJuridica juridicaEntity = mapperJuridica.toEntity(pessoaJuridica);

        Usuario usuario = new Usuario();
        usuario.setUsername(juridicaEntity.getEmail());
        String password = generateRandomPassword();
        usuario.setPassword(applicationConfig.passwordEncoder().encode(password));
        usuario.getAuthorities().add(getRoleJuridica());
        usuario.setDateLastPasswordChange(LocalDateTime.now());
        usuario.setPessoa(juridicaEntity);
        usuario.setEmpresa(juridicaEntity);

        usuario = repositoryUsuario.save(usuario);

        enviarEmail(usuario.getUsername(), password);

        return mapperJuridica.toDto(juridicaEntity);
    }

    private Role getRoleJuridica() {
        Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase("ROLE_JURIDICA");
        return optionalRole.orElseGet(() -> repositoryRole.save(Role.builder().authority("ROLE_JURIDICA").build()));
    }

    private String generateRandomPassword() {
        return Long.toString(Instant.now().toEpochMilli());
    }

    private void enviarEmail(String username, String password) {
            serviceEmail.sendMailWithAttachment(
                    EmailObject
                            .builder()
                            .destinatario(username)
                            .assunto("Acesso ao sistema")
                            .menssagem(EmailMessage.getDefaultMessage(username, password))
                            .build());
    }

//    private PessoaJuridica checkUs
}
