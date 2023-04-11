package com.pacoprojects.service;

import com.pacoprojects.dto.ItemNotaProdutoDto;
import com.pacoprojects.dto.projections.ItemNotaProdutoProjections;
import com.pacoprojects.mapper.ItemNotaProdutoMapper;
import com.pacoprojects.model.ItemNotaProduto;
import com.pacoprojects.repository.ItemNotaProdutoRepository;
import com.pacoprojects.repository.NotaFiscalCompraRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import com.pacoprojects.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemNotaProdutoService {

    private final ItemNotaProdutoRepository repositoryItemNotaProduto;
    private final NotaFiscalCompraRepository repositoryNotaFiscalCompra;
    private final ProdutoRepository repositoryProduto;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final ItemNotaProdutoMapper mapperItemNotaProduto;


    public List<ItemNotaProdutoProjections> getAllItemNotaProduto(Long idEmpresa) {
        return repositoryItemNotaProduto.findAllByEmpresa_Id(idEmpresa);
    }

    public ItemNotaProdutoProjections getItemNotaProdutoById(Long id) {
        ItemNotaProdutoProjections projections = repositoryItemNotaProduto.findItemNotaProdutoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "O item de Nota Fiscal de um produto não foi encontrado"));
        System.out.println(projections);
        return projections;
    }

    public ItemNotaProdutoDto addItemNotaProduto(ItemNotaProdutoDto itemNotaProdutoDto) {

        validateItemNotaProduto(itemNotaProdutoDto);

        ItemNotaProduto entity = repositoryItemNotaProduto.save(mapperItemNotaProduto.toEntity1(itemNotaProdutoDto));

        return mapperItemNotaProduto.toDto1(entity);
    }

    public void deleteItemNotaProduto(Long id) {

        if (repositoryItemNotaProduto.existsById(id)) {
            repositoryItemNotaProduto.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item da Nota Fiscal com produto não foi encontrado.");
        }
    }

    private void validateItemNotaProduto(ItemNotaProdutoDto itemNotaProdutoDto) {

        if (itemNotaProdutoDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O item de um nota fiscal de produto deve ser informado.");
        }

        if (itemNotaProdutoDto.notaFiscalCompra() == null || itemNotaProdutoDto.notaFiscalCompra().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota fiscal de compra deve ser informado.");
        } else if (!repositoryNotaFiscalCompra.existsById(itemNotaProdutoDto.notaFiscalCompra().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de nota fiscal inválido.");
        }

        if (itemNotaProdutoDto.produto() == null || itemNotaProdutoDto.produto().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto deve ser informado.");
        } else if (!repositoryProduto.existsById(itemNotaProdutoDto.produto().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de produto inválido.");
        }

        if (itemNotaProdutoDto.empresa() == null || itemNotaProdutoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A empresa deve ser informada.");
        } else if (!repositoryJuridica.existsById(itemNotaProdutoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código de empresa inválido.");
        }

        if (itemNotaProdutoDto.id() == null && repositoryItemNotaProduto.existsByProduto_IdAndNotaFiscalCompra_Id(itemNotaProdutoDto.produto().id(), itemNotaProdutoDto.notaFiscalCompra().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um item com esse produto e essa mesma nota fiscal.");
        }
    }
}
