package com.pacoprojects.service;

import com.pacoprojects.dto.CategoriaDto;
import com.pacoprojects.mapper.CategoriaMapper;
import com.pacoprojects.model.Categoria;
import com.pacoprojects.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repositoryCategoria;
    private final CategoriaMapper mapperCategoria;

    public CategoriaDto addCategoria(CategoriaDto categoriaDto) {
        Categoria entity = repositoryCategoria.save(mapperCategoria.toEntity(categoriaDto));
        return mapperCategoria.toDto(entity);
    }
}
