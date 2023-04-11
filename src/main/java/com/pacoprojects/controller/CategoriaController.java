package com.pacoprojects.controller;

import com.pacoprojects.dto.CategoriaDto;
import com.pacoprojects.dto.projections.CategoriaProjections;
import com.pacoprojects.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService serviceCategoria;

    @GetMapping
    public ResponseEntity<List<CategoriaProjections>> getAllCategorias(@RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceCategoria.getAllCategorias(idEmpresa));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CategoriaProjections> getCategoriaById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(serviceCategoria.getCategoriaById(id));
    }

    @GetMapping(path = "byName")
    public ResponseEntity<List<CategoriaProjections>> getAllCategoriasByName(@RequestParam(name = "name") String name,
                                                                     @RequestParam(name = "idEmpresa") Long idEmpresa) {
        return ResponseEntity.ok(serviceCategoria.getAllCategoriasByName(name, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> addCategoria(@Valid @RequestBody CategoriaDto categoria) {
        return ResponseEntity.ok(serviceCategoria.addCategoria(categoria));
    }

    @DeleteMapping(path = "{id}")
    public void deleteCategoria(@PathVariable(name = "id") Long id) {
        serviceCategoria.deleteCategoria(id);
    }
}
