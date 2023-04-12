package com.pacoprojects.controller;

import com.pacoprojects.dto.AvaliacaoProdutoDto;
import com.pacoprojects.dto.projections.AvaliacaoProdutoProjection;
import com.pacoprojects.service.AvaliacaoProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "avaliacaoProdutos")
@RequiredArgsConstructor
public class AvaliacaoProdutoController {

    private final AvaliacaoProdutoService serviceAvaliacaProduto;

    @GetMapping
    public ResponseEntity<List<AvaliacaoProdutoProjection>> getAllImagemProdutoByProdutoIdAndOrPessoaId(@RequestParam(name = "idProduto", required = false) Long idProduto,
                                                                                                        @RequestParam(name = "idPessoa", required = false) Long idPessoa) {
        return ResponseEntity.ok(serviceAvaliacaProduto.getAllImagemProdutoByProdutoIdAndOrPessoaId(idProduto, idPessoa));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoProdutoDto> addAvaliacaoProduto(@Valid @RequestBody AvaliacaoProdutoDto avaliacaoProduto) {
        return ResponseEntity.ok(serviceAvaliacaProduto.addAvaliacaoProduto(avaliacaoProduto));
    }

    @DeleteMapping(path = "{id}")
    public void deleteAvaliacaoProduto(@PathVariable(name = "id") Long id) {
        serviceAvaliacaProduto.deleteAvaliacaoProduto(id);
    }
}
