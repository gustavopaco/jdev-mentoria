package com.pacoprojects.service;

import com.pacoprojects.dto.CategoriaDto;
import com.pacoprojects.mapper.CategoriaMapper;
import com.pacoprojects.model.Categoria;
import com.pacoprojects.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repositoryCategoria;
    private final CategoriaMapper mapperCategoria;

    public List<CategoriaDto> getAllCategorias(Long idEmpresa) {
        return repositoryCategoria.findAllByEmpresa_Id(idEmpresa)
                .stream().map(mapperCategoria::toDto).collect(Collectors.toList());
    }

    public List<CategoriaDto> getAllCategoriasByName(String name, Long idEmpresa) {
        return repositoryCategoria.findAllByNomeContainsIgnoreCaseAndEmpresa_Id(name.trim(), idEmpresa)
                .stream().map(mapperCategoria::toDto).collect(Collectors.toList());
    }

    public CategoriaDto addCategoria(CategoriaDto categoriaDto) {

        validateCategoria(categoriaDto);

        Categoria entity = repositoryCategoria.save(mapperCategoria.toEntity(categoriaDto));
        return mapperCategoria.toDto(entity);
    }

    public void deleteCategoria(Long id) {

        if (repositoryCategoria.existsById(id)) {
            repositoryCategoria.deleteById(id);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não foi encontrada.");
    }

    private void validateCategoria(CategoriaDto categoria) {

        if (categoria.empresa() == null || categoria.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa deve ser informada.");
        }

        if (repositoryCategoria.existsByNomeIgnoreCaseAndEmpresa_Id(categoria.nome().trim(), categoria.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria já foi cadastrada.");
        }
    }
}
