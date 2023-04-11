package com.pacoprojects.controller;

import com.pacoprojects.dto.ContaPagarDto;
import com.pacoprojects.dto.projections.ContaPagarProjections;
import com.pacoprojects.service.ContaPagarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "contasPagar")
@RequiredArgsConstructor
public class ContaPagarController {

    private final ContaPagarService serviceContaPagar;

    @GetMapping
    public ResponseEntity<List<ContaPagarProjections>> getAllContaPagar(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceContaPagar.getAllContaPagar(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ContaPagarProjections> getContaPagarById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceContaPagar.getContaPagarById(id));
    }

    @GetMapping(path = "ByDescricao")
    public ResponseEntity<List<ContaPagarProjections>> getAllContaPagarByDescricao(@RequestParam(name = "descricao") String descricao) {
        return ResponseEntity.ok(serviceContaPagar.getAllContaPagarByDescricao(descricao));
    }

    @PostMapping
    public ResponseEntity<ContaPagarDto> addContaPagar(@Valid @RequestBody ContaPagarDto contaPagarDto) {
        return ResponseEntity.ok(serviceContaPagar.addContaPagar(contaPagarDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteContaPagar(@PathVariable(name = "id") Long id) {
        serviceContaPagar.deleteContaPagar(id);
    }
}
