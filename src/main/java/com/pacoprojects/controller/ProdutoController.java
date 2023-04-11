package com.pacoprojects.controller;

import com.pacoprojects.dto.ProdutoDto;
import com.pacoprojects.dto.projections.ProdutoProjections;
import com.pacoprojects.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService serviceProduto;

    @GetMapping
    public ResponseEntity<List<ProdutoProjections>> getAllProdutos(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceProduto.getAllProdutos(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ProdutoProjections> getProdutoById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceProduto.getProdutoById(id));
    }

    @GetMapping(path = "ByName")
    public ResponseEntity<List<ProdutoProjections>> getAllProdutosByName(@RequestParam(name = "name") String name,
                                                              @RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceProduto.getAllProdutosByName(name, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<ProdutoDto> addProduto(@Valid @RequestBody ProdutoDto categoria) {
        return ResponseEntity.ok(serviceProduto.addProduto(categoria));
    }

    @DeleteMapping(path = "{id}")
    public void deleteProduto(@PathVariable(name = "id") Long id) {
        serviceProduto.deleteProduto(id);
    }
}
