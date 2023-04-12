package com.pacoprojects.controller;

import com.pacoprojects.dto.ImagemProdutoDto;
import com.pacoprojects.dto.ImagemProdutoDtoRegister;
import com.pacoprojects.dto.projections.ImagemProdutoProjections;
import com.pacoprojects.service.ImagemProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "imagensProduto")
@RequiredArgsConstructor
public class ImagemProdutoController {

    private final ImagemProdutoService serviceImagemProduto;

    @GetMapping
    public ResponseEntity<List<ImagemProdutoProjections>> getAllImagemProdutoByProdutoId(@RequestParam(name = "idProduto") Long idProduto) {
        return ResponseEntity.ok(serviceImagemProduto.getAllByProdutoId(idProduto));
    }

    @PostMapping
    public ResponseEntity<ImagemProdutoDto> addImagemProduto(@RequestBody ImagemProdutoDtoRegister imagemProdutoDtoRegister) {
        return ResponseEntity.ok(serviceImagemProduto.addImagemProduto(imagemProdutoDtoRegister));
    }

    @DeleteMapping(path = "{id}")
    public void deleteImagemProduto(@PathVariable(name = "id") Long id) {
        serviceImagemProduto.deleteImagemProduto(id);
    }

    @DeleteMapping
    public void deleteAllImagensProduto(@RequestParam(name = "idProduto") Long idProduto) {
        serviceImagemProduto.deleteAllImagensProduto(idProduto);
    }
}
