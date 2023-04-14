package com.pacoprojects.controller;

import com.pacoprojects.dto.FormaPagamentoDto;
import com.pacoprojects.dto.projections.FormaPagamentoProjection;
import com.pacoprojects.service.FormaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "formaPagamento")
@RequiredArgsConstructor
public class FormaPagamentoController {

    private final FormaPagamentoService serviceFormaPagamento;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoProjection>> getAllFormaPagamento(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceFormaPagamento.getAllFormaPagamento(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<FormaPagamentoProjection> getFormaPagamentoById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceFormaPagamento.getFormaPagamentoById(id));
    }

    @GetMapping(path = "byName")
    public ResponseEntity<List<FormaPagamentoProjection>> getAllFormaPagamento() {
        return ResponseEntity.ok(serviceFormaPagamento.getAllFormaPagamento());
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDto> addFormaPagamento(@Valid @RequestBody FormaPagamentoDto formaPagamentoDto) {
        return ResponseEntity.ok(serviceFormaPagamento.addFormaPagamento(formaPagamentoDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteFormaPagamento(@PathVariable(name = "id") Long id) {
        serviceFormaPagamento.deleteFormaPagamento(id);
    }
}
