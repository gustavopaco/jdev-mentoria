package com.pacoprojects.controller;

import com.pacoprojects.dto.MarcaProdutoDto;
import com.pacoprojects.service.MarcaProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "marcasProdutos")
@RequiredArgsConstructor
public class MarcaProdutoController {

    private final MarcaProdutoService serviceMarcaProduto;

    @GetMapping
    public ResponseEntity<List<MarcaProdutoDto>> getAllMarcasProdutos(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceMarcaProduto.getAllMarcasProdutos(idEmpresa));
    }

    @GetMapping(path = "byName")
    public ResponseEntity<List<MarcaProdutoDto>> getAllMarcasProdutosByName(@RequestParam(name = "name") String name,
                                                                            @RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceMarcaProduto.getAllMarcasProdutosByName(name, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<MarcaProdutoDto> addMarcaProduto(@Valid @RequestBody MarcaProdutoDto marcaProdutoDto) {
        return ResponseEntity.ok(serviceMarcaProduto.addMarcaProduto(marcaProdutoDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteMarcaProduto(@PathVariable(name = "id") Long id) {
        serviceMarcaProduto.deleteMarcaProduto(id);
    }
}
