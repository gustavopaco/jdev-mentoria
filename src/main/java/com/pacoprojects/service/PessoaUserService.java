package com.pacoprojects.service;

import com.pacoprojects.api.ApiConsultaCep;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.email.EmailMessage;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.enums.TipoPessoa;
import com.pacoprojects.mapper.EnderecoMapper;
import com.pacoprojects.mapper.PessoaFisicaMapper;
import com.pacoprojects.mapper.PessoaJuridicaMapper;
import com.pacoprojects.model.*;
import com.pacoprojects.repository.*;
import com.pacoprojects.security.ApplicationConfig;
import com.pacoprojects.util.ValidadorCnpj;
import com.pacoprojects.util.ValidadorCpf;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaUserService {

    private final PessoaJuridicaRepository repositoryJuridica;
    private final PessoaFisicaRepository repositoryFisica;
    private final UsuarioRepository repositoryUsuario;
    private final RoleRepository repositoryRole;
    private final EnderecoRepository repositoryEndereco;
    private final ApplicationConfig applicationConfig;
    private final PessoaJuridicaMapper mapperJuridica;
    private final PessoaFisicaMapper mapperFisica;
    private final EnderecoMapper mapperEndereco;
    private final EmailService serviceEmail;
    private final ApiConsultaCep apiConsultaCep;

    public RegisterPessoaJuridicaDto addPessoaJuridica(RegisterPessoaJuridicaDto pessoaJuridica) {

        validatePessoaJurdicia(pessoaJuridica);

        PessoaJuridica juridicaEntity = mapperJuridica.toEntity(pessoaJuridica);

        consultAndPopulateAddress(juridicaEntity);

        saveNewUsuario(juridicaEntity, TipoPessoa.JURIDICA);

        return mapperJuridica.toDto(juridicaEntity);
    }

    public RegisterPessoaFisicaDto addPessoaFisica(RegisterPessoaFisicaDto pessoaFisicaDto) {

        validatePessoaFisica(pessoaFisicaDto);

        PessoaFisica fisicaEntity = mapperFisica.toEntity(pessoaFisicaDto);

        consultAndPopulateAddress(fisicaEntity);

        saveNewUsuario(fisicaEntity, TipoPessoa.FISICA);

        return mapperFisica.toDto(fisicaEntity);
    }


    public void saveNewUsuario(Pessoa pessoa, TipoPessoa tipoPessoa) {
        Usuario usuario = new Usuario();
        String password = generateRandomPassword();

        usuario.setUsername(pessoa.getEmail());
        usuario.setPassword(applicationConfig.passwordEncoder().encode(password));
        usuario.setDateLastPasswordChange(LocalDateTime.now());
        usuario.getAuthorities().add(getRole(tipoPessoa));
        usuario.setPessoa(pessoa);

        if (tipoPessoa.equals(TipoPessoa.JURIDICA)) {
            usuario.getAuthorities().add(getRole(TipoPessoa.ADMIN));
            usuario.setEmpresa(pessoa);
        } else if (tipoPessoa.equals(TipoPessoa.FISICA)) {
            usuario.setEmpresa(pessoa.getEmpresa());
        }

        repositoryUsuario.save(usuario);

        enviarEmail(usuario.getUsername(), password);
    }

    private void validatePessoaJurdicia(RegisterPessoaJuridicaDto pessoaJuridica) {

        if (pessoaJuridica == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não pode ser nula");
        }

        if (!ValidadorCnpj.isCNPJ(pessoaJuridica.cnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Cnpj " + pessoaJuridica.cnpj() + " inválido"));
        }

        if (pessoaJuridica.id() == null && repositoryJuridica.existsPessoaJuridicaByCnpj(pessoaJuridica.cnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa registrada com esse Cnpj: " + pessoaJuridica.cnpj());
        }

        if (pessoaJuridica.id() == null && repositoryJuridica.existsPessoaJuridicaByInscricaoEstadual(pessoaJuridica.inscricaoEstadual())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma empresa registrada com essa Inscrição Estadual.");
        }

        validateEmailInUse(pessoaJuridica.email());
    }

    private void validatePessoaFisica(RegisterPessoaFisicaDto pessoaFisica) {

        if (pessoaFisica == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa Física não pode ser nula");
        }

        if (!ValidadorCpf.isCPF(pessoaFisica.cpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Cpf " + pessoaFisica.cpf() + " inválido"));
        }

        if (pessoaFisica.id() == null && repositoryFisica.existsPessoaFisicaByCpf(pessoaFisica.cpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma pessoa registrada com esse Cpf: " + pessoaFisica.cpf());
        }

        validateEmailInUse(pessoaFisica.email());
    }

    private void consultAndPopulateAddress(Pessoa pessoa) {
        if (pessoa.getId() == null) {
            pessoa.setEnderecos(pessoa.getEnderecos().stream().peek(endereco -> {
                EnderecoDto enderecoDto = apiConsultaCep.getAdress(endereco.getCep());
                mapperEndereco.partialUpdate(enderecoDto, endereco);
            }).collect(Collectors.toSet()));
        } else {
            pessoa.setEnderecos(pessoa.getEnderecos().stream().peek(endereco -> {
                Optional<Endereco> optionalEndereco = repositoryEndereco.findById(endereco.getId());
                if (optionalEndereco.isPresent()
                        && (!optionalEndereco.get().getCep().equals(endereco.getCep())
                                || !optionalEndereco.get().getNumero().equals(endereco.getNumero()))) {
                    EnderecoDto enderecoDto = apiConsultaCep.getAdress(endereco.getCep());
                    mapperEndereco.partialUpdate(enderecoDto, endereco);
                }
            }).collect(Collectors.toSet()));
        }
    }

    private void validateEmailInUse(String username) {
        Optional<Usuario> optionalUsuario = repositoryUsuario.findUsuarioByUsername(username);

        if (optionalUsuario.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já está em uso");
        }
    }

    private Role getRole(TipoPessoa tipoPessoa) {
        if (tipoPessoa.equals(TipoPessoa.JURIDICA)) {
            Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase(TipoPessoa.JURIDICA.toString());
            return optionalRole.orElseGet(() -> repositoryRole.save(Role.builder().authority(TipoPessoa.JURIDICA.toString()).build()));
        } else if (tipoPessoa.equals(TipoPessoa.ADMIN)) {
            Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase(TipoPessoa.ADMIN.toString());
            return optionalRole.orElseGet(() -> repositoryRole.save(Role.builder().authority(TipoPessoa.ADMIN.toString()).build()));
        } else {
            Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase(TipoPessoa.FISICA.toString());
            return optionalRole.orElseGet(() -> repositoryRole.save(Role.builder().authority(TipoPessoa.FISICA.toString()).build()));
        }
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
}
