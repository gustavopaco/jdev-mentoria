package com.pacoprojects.service;

import com.pacoprojects.dto.NotaFiscalCompraDto;
import com.pacoprojects.dto.projections.NotaFiscalCompraProjections;
import com.pacoprojects.mapper.NotaFiscalCompraMapper;
import com.pacoprojects.model.NotaFiscalCompra;
import com.pacoprojects.repository.ItemNotaProdutoRepository;
import com.pacoprojects.repository.NotaFiscalCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaFiscalCompraService {

    private final NotaFiscalCompraRepository repositoryNotaFiscalCompra;
    private final ItemNotaProdutoRepository repositoryItemNotaProduto;
    private final NotaFiscalCompraMapper mapperNotaFiscalCompra;


    public List<NotaFiscalCompraProjections> getAllNotaFiscalCompraByDescricao(String descricao) {
        return repositoryNotaFiscalCompra.findAllByDescricaoContainsIgnoreCase(descricao);
    }

    public List<NotaFiscalCompraProjections> getAllNotaFiscalCompra(Long idEmpresa) {
        return repositoryNotaFiscalCompra.findAllByEmpresa_Id(idEmpresa);
    }

    public NotaFiscalCompraProjections getNotaFiscalCompraById(Long id) {
        return repositoryNotaFiscalCompra.findNotaFiscalCompraById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma nota fiscal de compra com o código: " + id));
    }

    public NotaFiscalCompraDto addNotaFiscalCompra(NotaFiscalCompraDto notaFiscalCompraDto) {

        validateNotaFiscalCompra(notaFiscalCompraDto);

        NotaFiscalCompra entity = repositoryNotaFiscalCompra.save(mapperNotaFiscalCompra.toEntity(notaFiscalCompraDto));

        return mapperNotaFiscalCompra.toDto(entity);
    }

    public void deleteNotaFiscalCompra(Long id) {

        Optional<NotaFiscalCompra> optionalNotaFiscalCompra = repositoryNotaFiscalCompra.findById(id);

        if (optionalNotaFiscalCompra.isPresent()) {
            repositoryItemNotaProduto.deleteAllByNotaFiscalCompra_Id(optionalNotaFiscalCompra.get().getId());
            repositoryNotaFiscalCompra.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado nenhuma nota fiscal com esse id: " + id);
        }
    }

    private void validateNotaFiscalCompra(NotaFiscalCompraDto notaFiscalCompraDto) {

        if (notaFiscalCompraDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota fiscal deve ser enviada.");
        }

        if (notaFiscalCompraDto.empresa() == null || notaFiscalCompraDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (notaFiscalCompraDto.contaPagar() == null || notaFiscalCompraDto.contaPagar().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca do produto deve ser informado.");
        }

        if (notaFiscalCompraDto.pessoa() == null || notaFiscalCompraDto.pessoa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota Fiscal do fornecedor do produto deve ser informado.");
        }

        if (notaFiscalCompraDto.id() == null && notaFiscalCompraDto.descricao() != null && !notaFiscalCompraDto.descricao().isBlank()) {
            if (repositoryNotaFiscalCompra.existsByDescricaoIgnoreCaseAndPessoa_Id(notaFiscalCompraDto.descricao().trim(), notaFiscalCompraDto.pessoa().id())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma Nota de compra com essa descrição.");
            }
        }
    }
}
