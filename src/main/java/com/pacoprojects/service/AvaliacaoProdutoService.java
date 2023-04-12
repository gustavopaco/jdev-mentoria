package com.pacoprojects.service;


import com.pacoprojects.dto.AvaliacaoProdutoDto;
import com.pacoprojects.dto.projections.AvaliacaoProdutoProjection;
import com.pacoprojects.mapper.AvaliacaoProdutoMapper;
import com.pacoprojects.model.AvaliacaoProduto;
import com.pacoprojects.repository.AvaliacaoProdutoRepository;
import com.pacoprojects.repository.PessoaFisicaRepository;
import com.pacoprojects.repository.PessoaJuridicaRepository;
import com.pacoprojects.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoProdutoService {

    private final AvaliacaoProdutoRepository repositoryAvaliacaoProduto;
    private final ProdutoRepository repositoryProduto;
    private final PessoaJuridicaRepository repositoryJuridica;
    private final PessoaFisicaRepository repositoryFisica;
    private final AvaliacaoProdutoMapper mapperAvaliacaoProduto;


    public List<AvaliacaoProdutoProjection> getAllImagemProdutoByProdutoIdAndOrPessoaId(Long idProduto, Long idPessoa) {

        if (idProduto != null && idPessoa != null) {
            return repositoryAvaliacaoProduto.findAllByProduto_IdAndPessoa_Id(idProduto, idPessoa);
        } else if (idProduto != null) {
            return repositoryAvaliacaoProduto.findAllByProduto_Id(idProduto);
        } else if (idPessoa != null) {
            return repositoryAvaliacaoProduto.findAllByPessoa_Id(idPessoa);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Por favor informe um dos tipos de busca, Pessoa e/ou Produto");
    }

    public AvaliacaoProdutoDto addAvaliacaoProduto(AvaliacaoProdutoDto avaliacaoProdutoDto) {

        validaAvaliacaoProduto(avaliacaoProdutoDto);

        AvaliacaoProduto entity = repositoryAvaliacaoProduto.save(mapperAvaliacaoProduto.toEntity(avaliacaoProdutoDto));

        return mapperAvaliacaoProduto.toDto(entity);
    }

    public void deleteAvaliacaoProduto(Long id) {

        if (repositoryAvaliacaoProduto.existsById(id)) {
            repositoryAvaliacaoProduto.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi encontrada avaliação com esse código.");
        }
    }

    private void validaAvaliacaoProduto(AvaliacaoProdutoDto avaliacaoProdutoDto) {

        if (avaliacaoProdutoDto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Avaliação não pode ser nula");
        }

        if (avaliacaoProdutoDto.produto() == null || avaliacaoProdutoDto.produto().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código do produto deve ser informado.");
        } else if (!repositoryProduto.existsById(avaliacaoProdutoDto.produto().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto informado não existe no sistema.");
        }

        if (avaliacaoProdutoDto.empresa() == null || avaliacaoProdutoDto.empresa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da empresa deve ser informado.");
        } else if (!repositoryJuridica.existsById(avaliacaoProdutoDto.empresa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa informada não existe no sistema.");
        }

        if (avaliacaoProdutoDto.pessoa() == null || avaliacaoProdutoDto.pessoa().id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código da pessoa física deve ser informado.");
        } else if (!repositoryFisica.existsById(avaliacaoProdutoDto.pessoa().id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa física informada não existe no sistema.");
        }

    }
}
