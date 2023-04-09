package com.pacoprojects.controller;

import com.pacoprojects.dto.PessoaJuridicaDto;
import com.pacoprojects.service.PessoaUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaUserService pessoaUserService;

    @PostMapping(path = "addJuridica")
    public ResponseEntity<PessoaJuridicaDto> addPessoaJuridica(@Valid @RequestBody PessoaJuridicaDto pessoaJuridica) {
        return ResponseEntity.ok(pessoaUserService.addPessoaJuridica(pessoaJuridica));
    }
}
