package com.pacoprojects;

import com.pacoprojects.controller.PessoaController;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.mapper.PessoaJuridicaMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class PessoaUsuarioTests {

    private final PessoaController controllerPessoa;
    private final PessoaJuridicaMapper mapper;

    @Autowired
    public PessoaUsuarioTests(PessoaController controllerPessoa, PessoaJuridicaMapper mapper) {
        this.controllerPessoa = controllerPessoa;
        this.mapper = mapper;
    }

    @Test
    void addPessoaJuridica() {

        EnderecoDto enderecoDto = EnderecoDto
                .builder()
                .rua("Rua do Indio")
                .cep("36985124")
                .numero("950")
                .bairro("CENTRO")
                .cidade("Belo Horizonte")
                .estado("MG")
                .tipoEndereco(TipoEndereco.COBRANCA)
                .build();

        EnderecoDto enderecoDto2 = EnderecoDto
                .builder()
                .rua("Rua as trincheiras")
                .cep("95462741")
                .numero("50")
                .bairro("Jardim America")
                .cidade("Belo Horizonte")
                .estado("MG")
                .tipoEndereco(TipoEndereco.ENTREGA)
                .build();

        TelefoneDto telefoneDto = TelefoneDto
                .builder()
                .numero("987650241")
                .build();

        RegisterPessoaJuridicaDto juridicaDto = RegisterPessoaJuridicaDto
                .builder()
                .cnpj("65.080.136/0001-02")
                .nome("Joao")
                .nomeFantasia("Supermercado do Joao")
                .email("joao@gmail.com")
                .inscricaoEstadual("re4t56er4t2f1gdf24g1df")
                .inscricaoMunicipal("ghg4jmgh4j5k4hgj5k4hj5k")
                .razaoSocial("Supermercado do Joao S/A")
                .telefones(Set.of(telefoneDto))
                .enderecos(Set.of(enderecoDto, enderecoDto2))
                .build();


        ResponseEntity<RegisterPessoaJuridicaDto> dtoResponseEntity = controllerPessoa.addPessoaJuridica(juridicaDto);

        assertNotNull(dtoResponseEntity.getBody());

        RegisterPessoaJuridicaDto responseEntityBody = dtoResponseEntity.getBody();

        assertNotNull(responseEntityBody.id());
        responseEntityBody.telefones().forEach(telefoneDto1 -> assertNotNull(telefoneDto1.id()));
        responseEntityBody.enderecos().forEach(enderecoDto1 -> assertNotNull(enderecoDto1.id()));



//        PessoaFisica pessoaFisica = new PessoaFisica();
//        pessoaFisica.setCpf("107.775.376-48");
//        pessoaFisica.setNome("Gustavo");
//        pessoaFisica.setEmail("gustavopaco@gmail.com");
//
//        Usuario usuario = new Usuario();
//        Pessoa pessoa = new PessoaFisica();
//        usuario.setPessoa(pessoa);
//        if (usuario.getPessoa() instanceof PessoaFisica) {
//           usuario.getPessoa().setNome("Gustavo");
//            ((PessoaFisica) usuario.getPessoa()).setCpf("107.775.376-48");
//            ((PessoaFisica) usuario.getPessoa()).setDataNascimento(LocalDate.of(1989,9,24));
//            usuario.getPessoa().setEmail("gustavopaco@gmail.com");
//            usuario.setUsername("gustavopaco@gmail.com");
//            usuario.setPassword(applicationConfig.passwordEncoder().encode("12345678"));
//            usuario.getAuthorities().add(repositoryRole.findById(1L).orElseThrow(() -> new RuntimeException("Nao existe")));
//            usuario.setDateLastPasswordChange(LocalDateTime.now());
//            System.out.println(usuario);
//            repositoryUsuario.save(usuario);
//        }
    }
}
