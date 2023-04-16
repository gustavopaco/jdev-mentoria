package com.pacoprojects.controller;

import com.pacoprojects.api.ApiConsultaReceitaAwsCnpj;
import com.pacoprojects.api.ApiConsultaViaCep;
import com.pacoprojects.api.integration.receitaaws.ConsultaReceitaAwsDto;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.PessoaFisicaDto;
import com.pacoprojects.dto.PessoaJuridicaDto;
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
    private final ApiConsultaViaCep apiConsultaViaCep;
    private final EndPointService endPointService;
    private final ApiConsultaReceitaAwsCnpj apiConsultaReceitaAwsCnpj;

    @PostMapping(path = "addJuridica")
    public ResponseEntity<PessoaJuridicaDto> addPessoaJuridica(@Valid @RequestBody PessoaJuridicaDto pessoaJuridica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaJuridica(pessoaJuridica));
    }

    @PostMapping(path = "addFisica")
    public ResponseEntity<PessoaFisicaDto> addPessoaFisica(@Valid @RequestBody PessoaFisicaDto pessoaFisica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaFisica(pessoaFisica));
    }

    @GetMapping(path = "consultViaCepApi/{cep}")
    public ResponseEntity<EnderecoDto> consultViaCepApi(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.ok(apiConsultaViaCep.consultViaCepApi(cep));
    }

    @GetMapping(path = "consultReceitaAwsApi")
    public ResponseEntity<ConsultaReceitaAwsDto> consultReceitaAwsApi(@RequestParam(name = "cnpj") String cnpj) {
        return ResponseEntity.ok(apiConsultaReceitaAwsCnpj.consultReceitaAwsApi(cnpj));
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
