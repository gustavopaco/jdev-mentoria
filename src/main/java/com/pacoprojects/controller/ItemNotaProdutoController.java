package com.pacoprojects.controller;

import com.pacoprojects.dto.ItemNotaProdutoDto;
import com.pacoprojects.dto.projections.ItemNotaProdutoProjections;
import com.pacoprojects.service.ItemNotaProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "itemNotaProduto")
@RequiredArgsConstructor
public class ItemNotaProdutoController {

    private final ItemNotaProdutoService serviceItemNotaProduto;

    @GetMapping
    public ResponseEntity<List<ItemNotaProdutoProjections>> getAllItemNotaProduto(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceItemNotaProduto.getAllItemNotaProduto(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ItemNotaProdutoProjections> getItemNotaProdutoById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceItemNotaProduto.getItemNotaProdutoById(id));
    }

//    @GetMapping(path = "ByDescricao")
//    public ResponseEntity<List<NotaFiscalCompraProjections>> getAllItemNotaProdutoByDescricao(@RequestParam(name = "descricao") String descricao) {
//        return ResponseEntity.ok(serviceNotaFiscalCompra.getAllNotaFiscalCompraByDescricao(descricao));
//    }

    @PostMapping
    public ResponseEntity<ItemNotaProdutoDto> addItemNotaProduto(@Valid @RequestBody ItemNotaProdutoDto notaFiscalCompraDto) {
        return ResponseEntity.ok(serviceItemNotaProduto.addItemNotaProduto(notaFiscalCompraDto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteItemNotaProduto(@PathVariable(name = "id") Long id) {
        serviceItemNotaProduto.deleteItemNotaProduto(id);
    }
}
