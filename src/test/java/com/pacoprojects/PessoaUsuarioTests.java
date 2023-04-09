package com.pacoprojects;

import com.pacoprojects.controller.PessoaController;
import com.pacoprojects.mapper.PessoaJuridicaMapper;
import com.pacoprojects.model.PessoaJuridica;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setCnpj("41.646.729/0001-17");
        pessoaJuridica.setNome("Jose Maria");
        pessoaJuridica.setNomeFantasia("Supermercado BH");
        pessoaJuridica.setEmail("josemaria@gmail.com");
        pessoaJuridica.setInscricaoEstadual("as4d56d64ad8w7ad4a");
        pessoaJuridica.setInscricaoMunicipal("a54547re4t6re4te56t4e54");
        pessoaJuridica.setRazaoSocial("Supermercado BH S/A");



        controllerPessoa.addPessoaJuridica(mapper.toDto(pessoaJuridica));

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
