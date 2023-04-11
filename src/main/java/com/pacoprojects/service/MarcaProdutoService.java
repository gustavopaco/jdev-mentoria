package com.pacoprojects.service;

import com.pacoprojects.dto.MarcaProdutoDto;
import com.pacoprojects.mapper.MarcaProdutoMapper;
import com.pacoprojects.model.MarcaProduto;
import com.pacoprojects.repository.MarcaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarcaProdutoService {

    private final MarcaProdutoRepository repositoryMarcaProduto;
    private final MarcaProdutoMapper mapperMarcaProduto;

    public List<MarcaProdutoDto> getAllMarcasProdutos(Long idEmpresa) {
        return repositoryMarcaProduto.findAllByEmpresa_Id(idEmpresa)
                .stream().map(mapperMarcaProduto::toDto).collect(Collectors.toList());
    }

    public MarcaProdutoDto getMarcaProdutoById(Long id) {
        MarcaProduto entity = repositoryMarcaProduto.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca do produto não foi encontrado."));
        return mapperMarcaProduto.toDto(entity);
    }

    public List<MarcaProdutoDto> getAllMarcasProdutosByName(String name, Long idEmpresa) {
        return repositoryMarcaProduto.findAllByNomeContainsIgnoreCaseAndEmpresa_Id(name, idEmpresa)
                .stream().map(mapperMarcaProduto::toDto).collect(Collectors.toList());
    }

    public MarcaProdutoDto addMarcaProduto(MarcaProdutoDto marcaProdutoDto) {

        validateMarcaProduto(marcaProdutoDto);

        MarcaProduto entity = repositoryMarcaProduto.save(mapperMarcaProduto.toEntity(marcaProdutoDto));

        return mapperMarcaProduto.toDto(entity);
    }

    public void deleteMarcaProduto(Long id) {
        if (repositoryMarcaProduto.existsById(id)) {
            repositoryMarcaProduto.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca de produto não foi encontrada.");
        }
    }

    private void validateMarcaProduto(MarcaProdutoDto marcaProdutoDto) {

        if (marcaProdutoDto.empresa() == null || marcaProdutoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (repositoryMarcaProduto.existsByNomeIgnoreCaseAndEmpresa_Id(marcaProdutoDto.nome().trim(), marcaProdutoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca de produto já foi cadastrada.");
        }
    }
}
