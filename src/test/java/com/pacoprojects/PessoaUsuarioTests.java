package com.pacoprojects;

import com.pacoprojects.controller.PessoaController;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.PessoaFisicaDto;
import com.pacoprojects.dto.PessoaJuridicaDto;
import com.pacoprojects.dto.TelefoneDto;
import com.pacoprojects.dto.projections.PessoaJuridicaProjection;
import com.pacoprojects.enums.TipoEndereco;
import com.pacoprojects.mapper.PessoaJuridicaMapper;
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
    private final PessoaJuridicaMapper mapperJuridica;


    @Autowired
    public PessoaUsuarioTests(PessoaController controllerPessoa, PessoaJuridicaRepository repositoryJuridica, PessoaJuridicaMapper mapperJuridica) {
        this.controllerPessoa = controllerPessoa;
        this.repositoryJuridica = repositoryJuridica;

        this.mapperJuridica = mapperJuridica;
    }

    @Test
    void addPessoaJuridica() {

        EnderecoDto enderecoDto = EnderecoDto
                .builder()
                .cep("30590-253")
                .numero("950")
                .tipoEndereco(TipoEndereco.COBRANCA)
                .build();

        EnderecoDto enderecoDto2 = EnderecoDto
                .builder()
                .cep("64053-390")
                .numero("50")
                .tipoEndereco(TipoEndereco.ENTREGA)
                .build();

        TelefoneDto telefoneDto = TelefoneDto
                .builder()
                .numero("445621574")
                .build();

        PessoaJuridicaDto juridicaDto = PessoaJuridicaDto
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


        ResponseEntity<PessoaJuridicaDto> dtoResponseEntity = controllerPessoa.addPessoaJuridica(juridicaDto);

        assertNotNull(dtoResponseEntity.getBody());

        PessoaJuridicaDto responseEntityBody = dtoResponseEntity.getBody();

        assertNotNull(responseEntityBody.id());
        responseEntityBody.telefones().forEach(telefoneDto1 -> assertNotNull(telefoneDto1.id()));
        responseEntityBody.enderecos().forEach(enderecoDto1 -> assertNotNull(enderecoDto1.id()));

    }

    @Test
    void addPessoaFisica() {

        EnderecoDto enderecoDto = EnderecoDto
                .builder()
                .cep("64053-390")
                .tipoEndereco(TipoEndereco.COBRANCA)
                .build();

        EnderecoDto enderecoDto2 = EnderecoDto
                .builder()
                .cep("30590-253")
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

        Optional<PessoaJuridicaProjection> optionalPessoaJuridica = repositoryJuridica.findByCnpj("78.643.209/0001-60");
        PessoaJuridica juridica = mapperJuridica.toEntityFromProjection(optionalPessoaJuridica
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe pessoa com esse ID")));
        PessoaFisicaDto pessoaFisicaDto = PessoaFisicaDto
                .builder()
                .nome("Gustavo")
                .cpf("107.775.376-48")
                .email("gustavopaco@gmail.com")
                .dataNascimento(LocalDate.of(1989,9,24))
                .telefones(Set.of(telefoneDto, telefoneDto2))
                .enderecos(Set.of(enderecoDto, enderecoDto2))
                .empresa(juridica)
                .build();

        ResponseEntity<PessoaFisicaDto> dtoResponseEntity = controllerPessoa.addPessoaFisica(pessoaFisicaDto);

        assertNotNull(dtoResponseEntity.getBody());

        PessoaFisicaDto responseEntityBody = dtoResponseEntity.getBody();

        assertNotNull(responseEntityBody.id());
        responseEntityBody.telefones().forEach(telefoneDto1 -> assertNotNull(telefoneDto1.id()));
        responseEntityBody.enderecos().forEach(enderecoDto1 -> assertNotNull(enderecoDto1.id()));

    }
}
