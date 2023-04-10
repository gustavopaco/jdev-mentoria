package com.pacoprojects.controller;

import com.pacoprojects.api.ApiConsultaCep;
import com.pacoprojects.dto.EnderecoDto;
import com.pacoprojects.dto.RegisterPessoaFisicaDto;
import com.pacoprojects.dto.RegisterPessoaJuridicaDto;
import com.pacoprojects.service.PessoaUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaUserService pessoaUserService;
    private final ApiConsultaCep apiConsultaCep;

    @PostMapping(path = "addJuridica")
    public ResponseEntity<RegisterPessoaJuridicaDto> addPessoaJuridica(@Valid @RequestBody RegisterPessoaJuridicaDto pessoaJuridica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaJuridica(pessoaJuridica));
    }

    @PostMapping(path = "addFisica")
    public ResponseEntity<RegisterPessoaFisicaDto> addPessoaFisica(@Valid @RequestBody RegisterPessoaFisicaDto pessoaFisica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaFisica(pessoaFisica));
    }

    @GetMapping(path = "consultAddress/{cep}")
    public ResponseEntity<EnderecoDto> consultAddress(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.ok(apiConsultaCep.getAdress(cep));
    }
}
