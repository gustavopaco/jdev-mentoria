package com.pacoprojects.controller;

import com.pacoprojects.api.ApiConsultaCep;
import com.pacoprojects.api.ApiConsultaCnpj;
import com.pacoprojects.dto.ConsultaReceitaAwsDto;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.dto.projections.PessoaFisicaProjection;
import com.pacoprojects.dto.projections.PessoaJuridicaProjection;
import com.pacoprojects.service.EndPointService;
import com.pacoprojects.service.PessoaUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaUserService pessoaUserService;
    private final ApiConsultaCep apiConsultaCep;
    private final EndPointService endPointService;
    private final ApiConsultaCnpj apiConsultaCnpj;

    @PostMapping(path = "addJuridica")
    public ResponseEntity<RegisterPessoaJuridicaDto> addPessoaJuridica(@Valid @RequestBody RegisterPessoaJuridicaDto pessoaJuridica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaJuridica(pessoaJuridica));
    }

    @PostMapping(path = "addFisica")
    public ResponseEntity<RegisterPessoaFisicaDto> addPessoaFisica(@Valid @RequestBody RegisterPessoaFisicaDto pessoaFisica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaFisica(pessoaFisica));
    }

    @GetMapping(path = "consultViaCepApi/{cep}")
    public ResponseEntity<EnderecoDto> consultViaCepApi(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.ok(apiConsultaCep.consultViaCepApi(cep));
    }

    @GetMapping(path = "consultReceitaAwsApi")
    public ResponseEntity<ConsultaReceitaAwsDto> consultReceitaAwsApi(@RequestParam(name = "cnpj") String cnpj) {
        return ResponseEntity.ok(apiConsultaCnpj.consultReceitaAwsApi(cnpj));
    }

    @GetMapping(path = "findFisicaByName")
    public ResponseEntity<List<PessoaFisicaProjection>> findPessoaFisicaByName(@RequestParam(name = "nome") String nome) {
        endPointService.updateEndPoint("findFisicaByName");
        return ResponseEntity.ok(pessoaUserService.findPessoaFisicaByName(nome));
    }

    @GetMapping(path = "findFisicaByCpf")
    public ResponseEntity<PessoaFisicaProjection> findPessoaFisicaByCpf(@RequestParam(name = "cpf") String cpf) {
        return ResponseEntity.ok(pessoaUserService.findPessoaFisicaByCpf(cpf));
    }

    @GetMapping(path = "findJuridicaByName")
    public ResponseEntity<List<PessoaJuridicaProjection>> findPessoaJuridicaByName(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(pessoaUserService.findPessoaJuridicaByName(nome));
    }

    @GetMapping(path = "findJuridicaByCnpj")
    public ResponseEntity<PessoaJuridicaProjection> findPessoaJuridicaByCnpj(@RequestParam(name = "cnpj") String cnpj) {
        return ResponseEntity.ok(pessoaUserService.findPessoaJuridicaByCnpj(cnpj));
    }
}
