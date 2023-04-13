package com.pacoprojects.controller;

import com.pacoprojects.dto.VendaCompraDto;
import com.pacoprojects.dto.projections.VendaCompraProjection;
import com.pacoprojects.dto.projections.VendaCompraProjectionSelected;
import com.pacoprojects.service.VendaCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "vendaCompra")
@RequiredArgsConstructor
public class VendaCompraController {

    private final VendaCompraService serviceVendaCompra;

    @GetMapping
    public ResponseEntity<List<VendaCompraProjection>> getAllVendaCompra(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceVendaCompra.getAllVendaCompra(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<VendaCompraProjectionSelected> getVendaCompraById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceVendaCompra.getVendaCompraById(id));
    }

    @GetMapping(path = "byName")
    public ResponseEntity<List<VendaCompraProjection>> getAllVendaCompraByName(@RequestParam(name = "name") String name,
                                                                             @RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceVendaCompra.getAllVendaCompraByName(name, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<VendaCompraDto> addVendaCompra(@Valid @RequestBody VendaCompraDto vendaCompraDto) {
        return ResponseEntity.ok(serviceVendaCompra.addVendaCompra(vendaCompraDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteVendaCompra(@PathVariable(name = "id") Long id) {
        serviceVendaCompra.deleteVendaCompra(id);
    }
}
