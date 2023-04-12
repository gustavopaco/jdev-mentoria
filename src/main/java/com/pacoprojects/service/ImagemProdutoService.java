package com.pacoprojects.service;

import com.pacoprojects.dto.ImagemProdutoDto;
import com.pacoprojects.dto.ImagemProdutoDtoRegister;
import com.pacoprojects.dto.projections.ImagemProdutoProjections;
import com.pacoprojects.mapper.ImagemProdutoMapper;
import com.pacoprojects.model.ImagemProduto;
import com.pacoprojects.repository.ImagemProdutoRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import com.pacoprojects.repository.ProdutoRepository;
import com.pacoprojects.util.GenerateMiniatureImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemProdutoService {

    private final ImagemProdutoRepository repositoryImagemProduto;
    private final ProdutoRepository repositoryProduto;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final ImagemProdutoMapper mapperImagemProduto;
    private final GenerateMiniatureImage generateMiniatureImage;


    public List<ImagemProdutoProjections> getAllByProdutoId(Long idProduto) {
        return repositoryImagemProduto.findAllByProduto_Id(idProduto);
    }

    public void deleteImagemProduto(Long id) {

        if (repositoryImagemProduto.existsById(id)) {
            repositoryImagemProduto.deleteById(id);
        } else {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrado a imagem desse produto");
        }
    }

    public void deleteAllImagensProduto(Long idProduto) {

        if (repositoryProduto.existsById(idProduto)) {
            repositoryImagemProduto.deleteAllByProduto_Id(idProduto);
        }
    }

    public ImagemProdutoDto addImagemProduto(ImagemProdutoDtoRegister imagemProdutoDtoRegister) {

        validateImagemProduto(imagemProdutoDtoRegister);

        ImagemProduto entity = mapperImagemProduto.toEntity(imagemProdutoDtoRegister);
        entity.setImagemMiniatura(
                generateMiniatureImage.getMiniature(entity.getImagemOriginal()));

        entity = repositoryImagemProduto.save(entity);

        return mapperImagemProduto.toDto1(entity);
    }

    private void validateImagemProduto(ImagemProdutoDtoRegister imagemProdutoDtoRegister) {

        if (imagemProdutoDtoRegister == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Imagem não pode ser nula.");
        }

        if (imagemProdutoDtoRegister.produto() == null || imagemProdutoDtoRegister.produto().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do produto deve ser informado.");
        } else if (!repositoryProduto.existsById(imagemProdutoDtoRegister.produto().id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto informado não existe no sistema.");
        }

        if (imagemProdutoDtoRegister.empresa() == null || imagemProdutoDtoRegister.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da empresa deve ser informado.");
        } else  if (!repositoryJuridica.existsById(imagemProdutoDtoRegister.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa informada não existe no sistema.");
        }

        if (repositoryImagemProduto.findAllByProduto_Id(imagemProdutoDtoRegister.produto().id()).size() >= 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já chegou ao limite de 6 imagens para esse produto, por favor apague uma antes de tentar de novo.");
        }

    }
}
