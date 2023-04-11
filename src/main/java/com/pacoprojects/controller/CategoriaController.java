package com.pacoprojects.controller;

import com.pacoprojects.dto.CategoriaDto;
import com.pacoprojects.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService serviceCategoria;

    @PostMapping(path = "addCategoria")
    public ResponseEntity<CategoriaDto> addCategoria(@Valid @RequestBody CategoriaDto categoria) {
        return ResponseEntity.ok(serviceCategoria.addCategoria(categoria));
    }
}
