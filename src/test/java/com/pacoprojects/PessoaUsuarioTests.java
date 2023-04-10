package com.pacoprojects;

import com.pacoprojects.controller.PessoaController;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.model.PessoaJuridica;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class PessoaUsuarioTests {

    private final PessoaController controllerPessoa;
    private final PessoaJuridicaRepository repositoryJuridica;


    @Autowired
    public PessoaUsuarioTests(PessoaController controllerPessoa, PessoaJuridicaRepository repositoryJuridica) {
        this.controllerPessoa = controllerPessoa;
        this.repositoryJuridica = repositoryJuridica;

    }

    @Test
    void addPessoaJuridica() {

        EnderecoDto enderecoDto = EnderecoDto
                .builder()
//                .rua("Rua do Indio")
                .cep("30590-253")
                .numero("950")
//                .bairro("CENTRO")
//                .cidade("Belo Horizonte")
//                .estado("MG")
                .tipoEndereco(TipoEndereco.COBRANCA)
                .build();

        EnderecoDto enderecoDto2 = EnderecoDto
                .builder()
//                .rua("Rua as trincheiras")
                .cep("64053-390")
                .numero("50")
//                .bairro("Jardim America")
//                .cidade("Belo Horizonte")
//                .estado("MG")
                .tipoEndereco(TipoEndereco.ENTREGA)
                .build();

        TelefoneDto telefoneDto = TelefoneDto
                .builder()
                .numero("445621574")
                .build();

        RegisterPessoaJuridicaDto juridicaDto = RegisterPessoaJuridicaDto
                .builder()
                .cnpj("08.235.782/0001-50")
                .nome("Marlon1")
                .nomeFantasia("Supermercado do marlon")
                .email("marlon1@gmail.com")
                .inscricaoEstadual("ggghxcvxvcxvghg")
                .inscricaoMunicipal("ggghxcvxvcxvghg")
                .razaoSocial("Supermercado da marlon1 S/A")
                .telefones(Set.of(telefoneDto))
                .enderecos(Set.of(enderecoDto, enderecoDto2))
                .build();


        ResponseEntity<RegisterPessoaJuridicaDto> dtoResponseEntity = controllerPessoa.addPessoaJuridica(juridicaDto);

        assertNotNull(dtoResponseEntity.getBody());

        RegisterPessoaJuridicaDto responseEntityBody = dtoResponseEntity.getBody();

        assertNotNull(responseEntityBody.id());
        responseEntityBody.telefones().forEach(telefoneDto1 -> assertNotNull(telefoneDto1.id()));
        responseEntityBody.enderecos().forEach(enderecoDto1 -> assertNotNull(enderecoDto1.id()));

    }

    @Test
    void addPessoaFisica() {

        EnderecoDto enderecoDto = EnderecoDto
                .builder()
//                .rua("Rua Sargento Johnny da Silva")
                .cep("64053-390")
//                .numero("200, Bloco 1 - Apt 201")
//                .bairro("Betânia")
//                .cidade("Belo Horizonte")
//                .estado("MG")
                .tipoEndereco(TipoEndereco.COBRANCA)
                .build();

        EnderecoDto enderecoDto2 = EnderecoDto
                .builder()
//                .rua("Rua Sargento Johnny da Silva")
                .cep("30590-253")
//                .numero("200, Bloco 1 - Apt 201")
//                .bairro("Betânia")
//                .cidade("Belo Horizonte")
//                .estado("MG")
                .tipoEndereco(TipoEndereco.ENTREGA)
                .build();

        TelefoneDto telefoneDto = TelefoneDto
                .builder()
                .numero("993039064")
                .build();

        TelefoneDto telefoneDto2 = TelefoneDto
                .builder()
                .numero("926451587")
                .build();

        Optional<PessoaJuridica> optionalPessoaJuridica = repositoryJuridica.findPessoaJuridicaByCnpj("78.643.209/0001-60");

        RegisterPessoaFisicaDto pessoaFisicaDto = RegisterPessoaFisicaDto
                .builder()
                .nome("Gustavo")
                .cpf("107.775.376-48")
                .email("gustavopaco@gmail.com")
                .dataNascimento(LocalDate.of(1989,9,24))
                .telefones(Set.of(telefoneDto, telefoneDto2))
                .enderecos(Set.of(enderecoDto, enderecoDto2))
                .empresa(optionalPessoaJuridica
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe pessoa com esse ID")))
                .build();

        ResponseEntity<RegisterPessoaFisicaDto> dtoResponseEntity = controllerPessoa.addPessoaFisica(pessoaFisicaDto);

        assertNotNull(dtoResponseEntity.getBody());

        RegisterPessoaFisicaDto responseEntityBody = dtoResponseEntity.getBody();

        assertNotNull(responseEntityBody.id());
        responseEntityBody.telefones().forEach(telefoneDto1 -> assertNotNull(telefoneDto1.id()));
        responseEntityBody.enderecos().forEach(enderecoDto1 -> assertNotNull(enderecoDto1.id()));

    }
}
