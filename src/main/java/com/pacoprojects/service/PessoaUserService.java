package com.pacoprojects.service;

import com.pacoprojects.api.ApiConsultaCep;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.dto.projections.PessoaFisicaProjection;
import com.pacoprojects.dto.projections.PessoaJuridicaProjection;
import com.pacoprojects.email.EmailMessage;
import com.pacoprojects.email.EmailObject;
import com.pacoprojects.email.EmailService;
import com.pacoprojects.enums.TipoPessoa;
import com.pacoprojects.enums.TipoRole;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public RegisterPessoaJuridicaDto addPessoaJuridica(RegisterPessoaJuridicaDto pessoaJuridicaDto) {

        validatePessoaJurdicia(pessoaJuridicaDto);

        PessoaJuridica juridicaEntity = mapperJuridica.toEntity(pessoaJuridicaDto);

        consultAndPopulateAddress(juridicaEntity);

        saveNewUsuario(juridicaEntity);

        return mapperJuridica.toDto(juridicaEntity);
    }

    public RegisterPessoaFisicaDto addPessoaFisica(RegisterPessoaFisicaDto pessoaFisicaDto) {

        validatePessoaFisica(pessoaFisicaDto);

        PessoaFisica fisicaEntity = mapperFisica.toEntity(pessoaFisicaDto);
        fisicaEntity.setTipoPessoa(TipoPessoa.FISICA);

        consultAndPopulateAddress(fisicaEntity);

        saveNewUsuario(fisicaEntity);

        return mapperFisica.toDto(fisicaEntity);
    }


    public void saveNewUsuario(Pessoa pessoa) {
        Usuario usuario = new Usuario();
        String password = generateRandomPassword();

        usuario.setUsername(pessoa.getEmail());
        usuario.setPassword(applicationConfig.passwordEncoder().encode(password));
        usuario.setDateLastPasswordChange(LocalDateTime.now());
        usuario.setAuthorities(getRoles(pessoa.getTipoPessoa()));
        usuario.setPessoa(pessoa);

        if (pessoa.getTipoPessoa().equals(TipoPessoa.JURIDICA)) {
            usuario.setEmpresa(pessoa);
        } else if (pessoa.getTipoPessoa().equals(TipoPessoa.FISICA)) {
            usuario.setEmpresa(pessoa.getEmpresa());
        }

        repositoryUsuario.save(usuario);

        enviarEmail(usuario.getUsername(), password);
    }

    private void validatePessoaJurdicia(RegisterPessoaJuridicaDto pessoaJuridica) {

        if (pessoaJuridica == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não pode ser nula");
        }

        if (pessoaJuridica.tipoPessoa() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Por favor selecione um tipo de Pessoa Jurídica.");
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
                EnderecoDto enderecoDto = apiConsultaCep.consultViaCepApi(endereco.getCep());
                mapperEndereco.partialUpdate(enderecoDto, endereco);
            }).collect(Collectors.toSet()));
        } else {
            pessoa.setEnderecos(pessoa.getEnderecos().stream().peek(endereco -> {
                Optional<Endereco> optionalEndereco = repositoryEndereco.findById(endereco.getId());
                if (optionalEndereco.isPresent()
                        && (!optionalEndereco.get().getCep().equals(endereco.getCep())
                                || !optionalEndereco.get().getNumero().equals(endereco.getNumero()))) {
                    EnderecoDto enderecoDto = apiConsultaCep.consultViaCepApi(endereco.getCep());
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

    private Set<Role> getRoles(TipoPessoa tipoPessoa) {
        Set<Role> authorities = new LinkedHashSet<>();
        if (tipoPessoa.equals(TipoPessoa.JURIDICA)) {
            authorities.add(verifyAndAddRole(TipoRole.JURIDICA));
            authorities.add(verifyAndAddRole(TipoRole.ADMIN));
        } else if (tipoPessoa.equals(TipoPessoa.JURIDICA_FORNECEDOR)) {
            authorities.add(verifyAndAddRole(TipoRole.FORNECEDOR));
        } else if (tipoPessoa.equals(TipoPessoa.FISICA)){
            authorities.add(verifyAndAddRole(TipoRole.FISICA));
        }
        return authorities;
    }

    private Role verifyAndAddRole(TipoRole tipoRole) {
        Optional<Role> optionalRole = repositoryRole.findRoleByAuthorityContainsIgnoreCase(tipoRole.toString());
        return optionalRole.orElseGet(() -> repositoryRole.save(Role.builder().authority(tipoRole.toString()).build()));
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

    public List<PessoaFisicaProjection> findPessoaFisicaByName(String nome) {
        return repositoryFisica.findAllByNomeContainsIgnoreCase(nome.trim());
    }

    public PessoaFisicaProjection findPessoaFisicaByCpf(String cpf) {
        return repositoryFisica.findByCpf(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe Pessoa com esse Cpf: " + cpf));
    }

    public List<PessoaJuridicaProjection> findPessoaJuridicaByName(String nome) {
        return repositoryJuridica.findAllByNomeContainsIgnoreCase(nome.trim());
    }

    public PessoaJuridicaProjection findPessoaJuridicaByCnpj(String cnpj) {
        return repositoryJuridica.findByCnpj(cnpj).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe empresa com esse Cnpj: " + cnpj));
    }


}
