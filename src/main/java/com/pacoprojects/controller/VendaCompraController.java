package com.pacoprojects.controller;

import com.pacoprojects.dto.VendaCompraDto;
import com.pacoprojects.dto.projections.ItemVendaCompraSelected;
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
    public ResponseEntity<List<VendaCompraProjectionSelected>> getAllVendaCompra(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceVendaCompra.getAllVendaCompra(idEmpresa));
    }

    @GetMapping(path = "consultaVenda")
    public ResponseEntity<List<ItemVendaCompraSelected>> getAllVendaCompraByParam(@RequestParam(name = "idProduto", required = false) Long idProduto,
                                                                                  @RequestParam(name = "nomeProduto", required = false) String nomeProduto,
                                                                                  @RequestParam(name = "nomeCliente", required = false) String nomeCliente,
                                                                                  @RequestParam(name = "endCobranca", required = false) String endCobranca,
                                                                                  @RequestParam(name = "endEntrega", required = false) String endEntrega) {
        return ResponseEntity.ok(serviceVendaCompra.getAllVendaCompraByParam(idProduto, nomeProduto, nomeCliente, endCobranca, endEntrega));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<VendaCompraProjectionSelected> getVendaCompraById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceVendaCompra.getVendaCompraById(id));
    }

    @PostMapping
    public ResponseEntity<VendaCompraDto> addVendaCompra(@Valid @RequestBody VendaCompraDto vendaCompraDto) {
        return ResponseEntity.ok(serviceVendaCompra.addVendaCompra(vendaCompraDto));
    }

    @PutMapping
    public void enableVendaCompra(@RequestParam(name = "idVenda") Long idVenda) {
        serviceVendaCompra.enableVendaCompra(idVenda);
    }

    @DeleteMapping(path = "{id}")
    public void deleteVendaCompra(@PathVariable(name = "id") Long id) {
        serviceVendaCompra.deleteVendaCompra(id);
    }
}
