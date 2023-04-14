package com.pacoprojects.controller;

import com.pacoprojects.dto.CupomDescontoDto;
import com.pacoprojects.service.CupomDescontoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "cupomDesconto")
@RequiredArgsConstructor
public class CupomDescontoController {

    private final CupomDescontoService serviceCupomDesconto;

    @GetMapping
    public ResponseEntity<List<CupomDescontoDto>> getAllCupomDescontoByEmpresaId(Long idEmpresa) {
        return ResponseEntity.ok(serviceCupomDesconto.getAllCupomDescontoByEmpresaId(idEmpresa));
    }

    @GetMapping(path = "{idCupom}")
    public ResponseEntity<CupomDescontoDto> getCupomDescontoById(@PathVariable(name = "idCupom")
                                                                    Long idCupom) {
        return ResponseEntity.ok(serviceCupomDesconto.getCupomDescontoById(idCupom));
    }

    @GetMapping(path = "ByDescricao")
    public ResponseEntity<CupomDescontoDto> getCupomDescontoByDescricao(@RequestParam(name = "codigoDescricao")
                                                                        String codigoDescricao,
                                                                        @RequestParam(name = "idEmpresa")
                                                                        Long idEmpresa) {
        return ResponseEntity.ok(serviceCupomDesconto.getCupomDescontoByDescricao(codigoDescricao, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<CupomDescontoDto> addCupomDesconto(@Valid @RequestBody CupomDescontoDto cupomDescontoDto) {
        return ResponseEntity.ok(serviceCupomDesconto.addCupomDesconto(cupomDescontoDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteCupomDesconto(@PathVariable(name = "id") Long id) {
        serviceCupomDesconto.deleteCupomDesconto(id);
    }
}
