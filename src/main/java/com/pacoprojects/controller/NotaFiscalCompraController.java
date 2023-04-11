package com.pacoprojects.controller;

import com.pacoprojects.dto.NotaFiscalCompraDto;
import com.pacoprojects.dto.projections.NotaFiscalCompraProjections;
import com.pacoprojects.service.NotaFiscalCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "notaFiscalCompra")
@RequiredArgsConstructor
public class NotaFiscalCompraController {

    private final NotaFiscalCompraService serviceNotaFiscalCompra;

    @GetMapping
    public ResponseEntity<List<NotaFiscalCompraProjections>> getAllNotaFiscalCompra(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceNotaFiscalCompra.getAllNotaFiscalCompra(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<NotaFiscalCompraProjections> getNotaFiscalCompraById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceNotaFiscalCompra.getNotaFiscalCompraById(id));
    }

    @GetMapping(path = "ByDescricao")
    public ResponseEntity<List<NotaFiscalCompraProjections>> getAllNotaFiscalCompraByDescricao(@RequestParam(name = "descricao") String descricao) {
        return ResponseEntity.ok(serviceNotaFiscalCompra.getAllNotaFiscalCompraByDescricao(descricao));
    }

    @PostMapping
    public ResponseEntity<NotaFiscalCompraDto> addNotaFiscalCompra(@Valid @RequestBody NotaFiscalCompraDto notaFiscalCompraDto) {
        return ResponseEntity.ok(serviceNotaFiscalCompra.addNotaFiscalCompra(notaFiscalCompraDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteNotaFiscalCompra(@PathVariable(name = "id") Long id) {
        serviceNotaFiscalCompra.deleteNotaFiscalCompra(id);
    }
}
