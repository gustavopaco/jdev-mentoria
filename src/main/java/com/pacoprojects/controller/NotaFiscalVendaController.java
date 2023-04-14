package com.pacoprojects.controller;

import com.pacoprojects.dto.projections.NotaFiscalVendaProjection;
import com.pacoprojects.service.NotaFiscalVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "notaFiscalVenda")
@RequiredArgsConstructor
public class NotaFiscalVendaController {

    private final NotaFiscalVendaService serviceNotaFiscalVenda;

    @GetMapping(path = "{idVenda}")
    public ResponseEntity<NotaFiscalVendaProjection> getNotaFiscalByIdVenda(@PathVariable(name = "idVenda") Long idVenda) {
        return ResponseEntity.ok(serviceNotaFiscalVenda.getNotaFiscalByIdVenda(idVenda));
    }
}
